package com.example.auction_service.services.auction;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.exceptions.auction.InvalidCurrencyException;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.dtos.AuctionInputDTO;
import com.example.auction_service.models.auction.dtos.AuctionProviderRequestDTO;
import com.example.auction_service.models.auction.dtos.AuctionRequestDTO;
import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.models.auction.enums.StatusAuction;
import com.example.auction_service.models.item.Item;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.repositories.AuctionRepository;
import com.example.auction_service.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;

    public Auction getAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction", "No auction found with id: " + auctionId));
    }

    public void deleteAuctionById(Long auctionId) {
        Auction auction = getAuctionById(auctionId);
        auction.setIsActive(false);
        auctionRepository.saveAndFlush(auction);
    }

    public Auction createAuction(AuctionInputDTO auctionInputDTO, Provider provider, List<Long> itemIds) {
        if (!Currency.isValid(String.valueOf(auctionInputDTO.currency()))) {
            throw new InvalidCurrencyException("currency", "Invalid currency: " + auctionInputDTO.currency());
        }
        Currency currency = Currency.valueOf(auctionInputDTO.currency().name());

        LocalDateTime auctionEndDate = auctionInputDTO.auctionDate().plusMinutes(auctionInputDTO.duration());

        Auction auction = new Auction(
                provider,
                auctionInputDTO.auctionName(),
                auctionInputDTO.category(),
                auctionInputDTO.description(),
                auctionInputDTO.duration(),
                auctionInputDTO.price(),
                currency,
                null, // currentBid jest null, ponieważ nie ma jeszcze ofert
                auctionInputDTO.auctionDate(),
                auctionEndDate,
                auctionInputDTO.isBuyNow(),
                auctionInputDTO.buyNowPrice(),
                auctionInputDTO.isBuyNowCompleted()
        );
        assignItemsToAuction(auction.getId(), itemIds);

        return auctionRepository.saveAndFlush(auction);
    }

    public Page<Auction> getAuctions(AuctionRequestDTO auctionRequestDTO) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(auctionRequestDTO.getPage()),
                Integer.parseInt(auctionRequestDTO.getSize()),
                Sort.Direction.valueOf(auctionRequestDTO.getDirection()),
                auctionRequestDTO.getSortParam());

        Specification<Auction> spec = Specification.where(null);
        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isActive")));

        if (auctionRequestDTO.getAuctionNameSearch() != null) {
            spec = spec.and(AuctionSpecification.auctionNameContains(auctionRequestDTO.getAuctionNameSearch()));
        }
        if (auctionRequestDTO.getPriceFrom() != null) {
            spec = spec.and(AuctionSpecification.priceGreaterThanOrEqual(auctionRequestDTO.getPriceFrom()));
        }
        if (auctionRequestDTO.getPriceTo() != null) {
            spec = spec.and(AuctionSpecification.priceLessThanOrEqual(auctionRequestDTO.getPriceTo()));
        }
        if (auctionRequestDTO.getProviderId() != null) {
            spec = spec.and(AuctionSpecification.providerIdEquals(auctionRequestDTO.getProviderId()));
        }
        if (auctionRequestDTO.getAuctionCategorySearch() != null) {
            spec = spec.and(AuctionSpecification.auctionNameContains(auctionRequestDTO.getAuctionCategorySearch()));
        }

        return auctionRepository.findAll(spec, pageRequest);
    }

    public Page<Auction> getAuctionsByProviderId(Long providerId, AuctionProviderRequestDTO auctionProviderRequestDTO) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(auctionProviderRequestDTO.getPage()),
                Integer.parseInt(auctionProviderRequestDTO.getSize()),
                Sort.Direction.valueOf(auctionProviderRequestDTO.getDirection()),
                auctionProviderRequestDTO.getSortParam());
        return auctionRepository.findAllByProviderIdAndIsActiveTrue(providerId, pageRequest);
    }
    public Auction updateAuctionById(Long auctionId, AuctionInputDTO auctionInputDTO, List<Long> newItemIds) {
        if (!Currency.isValid(String.valueOf(auctionInputDTO.currency()))) {
            throw new InvalidCurrencyException("currency", "Invalid currency: " + auctionInputDTO.currency());
        }
        Auction auction = getAuctionById(auctionId);
        auction.setAuctionName(auctionInputDTO.auctionName());
        auction.setDescription(auctionInputDTO.description());
        auction.setPrice(auctionInputDTO.price());
        auction.setDuration(auctionInputDTO.duration());
        auction.setIsActive(auctionInputDTO.isActive());
        auction.setAuctionDate(auctionInputDTO.auctionDate());
        auction.setAuctionDateEnd(auctionInputDTO.auctionDateEnd());
        auction.setIsBuyNow(auctionInputDTO.isBuyNow());
        auction.setBuyNowPrice(auctionInputDTO.buyNowPrice());
        auction.setCategory(auctionInputDTO.category());

        Currency currency = Currency.valueOf(String.valueOf(auctionInputDTO.currency()));
        auction.setCurrency(currency);

        auction.setStatusAuction(StatusAuction.NOT_STARTED);

        assignItemsToAuction(auctionId, newItemIds);

        return auctionRepository.saveAndFlush(auction);
    }

    public String getProviderEmailByAuctionId(Long auctionId) {
        return auctionRepository.getProviderEmailByAuctionId(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "No Auction found with provider id: " + auctionId));
    }

    public Auction activateAuction(Long auctionId) {
        Auction auction = getAuctionById(auctionId);
        if (auction != null) {
            auction.setStatusAuction(StatusAuction.ACTIVE);
            return auctionRepository.save(auction);
        } else {
            // Handle the case where the auction doesn't exist
            throw new EntityNotFoundException("Auction ", "Auction not found with id: " + auctionId);
        }
    }

    public Auction deactivateAuction(Long auctionId) {
        Auction auction = getAuctionById(auctionId);
        if (auction != null) {
            auction.setStatusAuction(StatusAuction.CANCELED);
            return auctionRepository.save(auction);
        } else {
            // Handle the case where the auction doesn't exist
            throw new EntityNotFoundException("Auction ", "Auction not found with id: " + auctionId);
        }
    }

    public Auction assignItemsToAuction(Long auctionId, List<Long> itemIds) {
        Auction auction = getAuctionById(auctionId);
        List<Item> items = itemRepository.findAllById(itemIds);
        for (Item item : items) {
            item.setAuction(auction); // Przypisanie aukcji do przedmiotu
            itemRepository.save(item); // Zapisanie zmian w przedmiocie
        }
        return auction;
    }





}
