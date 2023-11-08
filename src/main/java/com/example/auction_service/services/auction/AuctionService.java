package com.example.auction_service.services.auction;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.exceptions.auction.InvalidCurrencyException;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.dtos.AuctionInputDTO;
import com.example.auction_service.models.auction.dtos.AuctionProviderRequestDTO;
import com.example.auction_service.models.auction.dtos.AuctionRequestDTO;
import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.repositories.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public Auction getAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction", "No auction found with id: " + auctionId));
    }

    public void deleteAuctionById(Long auctionId) {
        Auction auction = getAuctionById(auctionId);
        auction.setIsActive(false);
        auctionRepository.saveAndFlush(auction);
    }

    public Auction createAuction(AuctionInputDTO auctionInputDTO, Provider provider) {
        if (!Currency.isValid(String.valueOf(auctionInputDTO.currency()))) {
            throw new InvalidCurrencyException("currency", "Invalid currency: " + auctionInputDTO.currency());
        }
        Currency currency = Currency.valueOf(auctionInputDTO.currency().name());

        LocalDateTime auctionEndDate = auctionInputDTO.auctionDate().plusMinutes(auctionInputDTO.duration());

        Auction auction = new Auction(
                provider,
                auctionInputDTO.auctionName(),
                auctionInputDTO.description(),
                auctionInputDTO.duration(),
                auctionInputDTO.price(),
                currency,
                auctionInputDTO.price(), // Assuming the initial current bid is the starting price
                auctionInputDTO.auctionDate(),
                auctionEndDate,
                auctionInputDTO.isBuyNow(),
                auctionInputDTO.buyNowPrice()
        );

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

        return auctionRepository.findAll(spec, pageRequest);
    }

    public Page<Auction> getAuctionsByProviderId(Long providerId, AuctionProviderRequestDTO offeringProviderRequestDTO) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(offeringProviderRequestDTO.getPage()),
                Integer.parseInt(offeringProviderRequestDTO.getSize()),
                Sort.Direction.valueOf(offeringProviderRequestDTO.getDirection()),
                offeringProviderRequestDTO.getSortParam());
        return auctionRepository.findAllByProviderIdAndIsActiveTrue(providerId, pageRequest);
    }


}
