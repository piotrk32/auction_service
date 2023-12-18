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
        if (auction != null) {
            auction.setStatusAuction(StatusAuction.DELETED);

            List<Item> items = itemRepository.findAllByAuctionId(auctionId);
            for (Item item : items) {
                item.setAuction(null);
                itemRepository.save(item);
            }

            auctionRepository.save(auction);
        } else {
            throw new EntityNotFoundException("Auction ", "Auction not found with id: " + auctionId);
        }
    }

    public Auction createAuction(AuctionInputDTO auctionInputDTO, Provider provider, List<Long> itemIds) {
        if (!Currency.isValid(String.valueOf(auctionInputDTO.currency()))) {
            throw new InvalidCurrencyException("currency", "Invalid currency: " + auctionInputDTO.currency());
        }
        Currency currency = Currency.valueOf(auctionInputDTO.currency().name());

        LocalDateTime auctionStartDate = auctionInputDTO.auctionDate();
        LocalDateTime auctionEndDate = auctionInputDTO.auctionDateEnd();

        Auction auction = new Auction(
                provider,
                auctionInputDTO.auctionName(),
                auctionInputDTO.category(),
                auctionInputDTO.description(),
                Currency.valueOf(auctionInputDTO.currency().name()),
                auctionInputDTO.auctionDate(),
                auctionInputDTO.auctionDateEnd(),
                auctionInputDTO.isBuyNow(),
                auctionInputDTO.buyNowPrice(),
                auctionInputDTO.price()
        );

        Auction savedAuction = auctionRepository.saveAndFlush(auction);

        assignItemsToAuction(savedAuction.getId(), itemIds);

        return savedAuction; // Zwróć zapisaną aukcję
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

//        if (auctionRequestDTO.getAuctionNameSearch() != null) {
//            spec = spec.and(AuctionSpecification.auctionNameContains(auctionRequestDTO.getAuctionNameSearch()));
//        }
//        if (auctionRequestDTO.getPriceFrom() != null) {
//            spec = spec.and(AuctionSpecification.priceGreaterThanOrEqual(auctionRequestDTO.getPriceFrom()));
//        }
//        if (auctionRequestDTO.getPriceTo() != null) {
//            spec = spec.and(AuctionSpecification.priceLessThanOrEqual(auctionRequestDTO.getPriceTo()));
//        }
//        if (auctionRequestDTO.getProviderId() != null) {
//            spec = spec.and(AuctionSpecification.providerIdEquals(auctionRequestDTO.getProviderId()));
//        }
//        if (auctionRequestDTO.getAuctionCategorySearch() != null) {
//            spec = spec.and(AuctionSpecification.auctionNameContains(auctionRequestDTO.getAuctionCategorySearch()));
//        }

        return auctionRepository.findAll(pageRequest);
    }

//    public Page<Auction> getAuctionsByProviderId(Long providerId, AuctionProviderRequestDTO auctionProviderRequestDTO) {
//        PageRequest pageRequest = PageRequest.of(
//                Integer.parseInt(auctionProviderRequestDTO.getPage()),
//                Integer.parseInt(auctionProviderRequestDTO.getSize()),
//                Sort.Direction.valueOf(auctionProviderRequestDTO.getDirection()),
//                auctionProviderRequestDTO.getSortParam());
//
//        // Załóżmy, że chcesz szukać aktywnych aukcji
//        return auctionRepository.findAllByProviderIdAndStatusAuction(providerId, StatusAuction.FINISHED, pageRequest);
//    }

    public Page<Auction> getAuctionsByProviderId(Long providerId, AuctionProviderRequestDTO auctionProviderRequestDTO) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(auctionProviderRequestDTO.getPage()),
                Integer.parseInt(auctionProviderRequestDTO.getSize()),
                Sort.Direction.valueOf(auctionProviderRequestDTO.getDirection()),
                auctionProviderRequestDTO.getSortParam());

        return auctionRepository.findAllByProviderId(providerId,  pageRequest);
    }
    public Auction updateAuctionById(Long auctionId, AuctionInputDTO auctionInputDTO, List<Long> newItemIds) {
        Auction auction = getAuctionById(auctionId);

        auction.setAuctionName(auctionInputDTO.auctionName());
        auction.setDescription(auctionInputDTO.description());
        auction.setPrice(auctionInputDTO.price());
        auction.setAuctionDate(auctionInputDTO.auctionDate());
        auction.setAuctionDateEnd(auctionInputDTO.auctionDateEnd());
        auction.setIsBuyNow(auctionInputDTO.isBuyNow());

        // Walidacja ceny "buy now" w kontekście aktywacji opcji "buy now"
        if (auctionInputDTO.isBuyNow() && (auctionInputDTO.buyNowPrice() == null || auctionInputDTO.buyNowPrice() <= 0)) {
            throw new IllegalArgumentException("Buy now price must be provided and positive when buy now is active");
        }
        auction.setBuyNowPrice(auctionInputDTO.buyNowPrice());

        auction.setCategory(auctionInputDTO.category());

        Currency currency = Currency.valueOf(String.valueOf(auctionInputDTO.currency()));
        auction.setCurrency(currency);

        if (newItemIds != null && !newItemIds.isEmpty()) {
            // Jeśli jest, przypisz nowe przedmioty do aukcji
            assignItemsToAuction(auctionId, newItemIds);
        }

        return auctionRepository.saveAndFlush(auction);
    }

    public String getProviderEmailByAuctionId(Long auctionId) {
        return auctionRepository.getProviderEmailByAuctionId(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "No Auction found with provider id: " + auctionId));
    }

    public Auction activateAuction(Long auctionId) {
        Auction auction = getAuctionById(auctionId);

//        // Sprawdź, czy aukcja ma przypisane jakieś przedmioty
//        List<Item> items = itemRepository.findAllByAuction(auction);
//        if (items == null || items.isEmpty()) {
//            throw new IllegalStateException("Cannot activate auction without items.");
//        }

        // Sprawdź, czy aukcja już nie jest aktywna
        if (auction.getStatusAuction() != StatusAuction.ACTIVE) {
            LocalDateTime now = LocalDateTime.now(); // Pobranie bieżącej daty i czasu

            auction.setStatusAuction(StatusAuction.ACTIVE);
            auction.setIsBuyNow(false); // Wyłączenie opcji "Kup Teraz"
            auction.setAuctionDate(now); // Ustawienie bieżącej daty i czasu jako daty rozpoczęcia aukcji
            auction.setAuctionDateEnd(now.plusMinutes(auction.getDuration())); // Ustawienie daty zakończenia aukcji

            auctionRepository.save(auction);
        } else {
            throw new IllegalStateException("Auction with id: " + auctionId + " is already ACTIVE.");
        }

        return auction;
    }

    public Auction deactivateAuction(Long auctionId) {
        Auction auction = getAuctionById(auctionId);

        if (auction.getStatusAuction() == StatusAuction.ACTIVE) {
            LocalDateTime now = LocalDateTime.now(); // Pobranie bieżącej daty i czasu

            auction.setStatusAuction(StatusAuction.CANCELED);
            auction.setAuctionDateEnd(now); // Ustawienie daty zakończenia aukcji na bieżącą datę i czas

            return auctionRepository.save(auction);
        } else {
            // Jeśli aukcja nie jest w stanie ACTIVE, rzucamy wyjątek lub obsługujemy inaczej
            throw new IllegalStateException("Auction with id: " + auctionId + " is not in ACTIVE state and cannot be canceled.");
        }
    }

    public Auction assignItemsToAuction(Long auctionId, List<Long> itemIds) {
        Auction auction = getAuctionById(auctionId);

        // Sprawdź, czy status aukcji nie jest ACTIVE
        if (auction.getStatusAuction() == StatusAuction.ACTIVE) {
            throw new IllegalStateException("Cannot assign items to an active auction.");
        }

        List<Item> items = itemRepository.findAllById(itemIds);
        if (items.size() != itemIds.size()) {
            throw new EntityNotFoundException("Item", "One or more items not found with provided IDs");
        }

        for (Item item : items) {
            // Sprawdź, czy przedmiot nie został już sprzedany
            if (item.getIsSold()) {
                throw new IllegalStateException("Cannot assign sold item to an auction. Item ID: " + item.getId());
            }

            item.setAuction(auction); // Przypisanie aukcji do przedmiotu
            item.setIsBuyNowActive(false); // Ustawienie isBuyNowActive na false
            itemRepository.save(item); // Zapisanie zmian w przedmiocie
        }
        return auction;
    }





}
