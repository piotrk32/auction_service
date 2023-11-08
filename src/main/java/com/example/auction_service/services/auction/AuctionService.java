package com.example.auction_service.services.auction;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.exceptions.auction.InvalidCurrencyException;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.dtos.AuctionInputDTO;
import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.repositories.AuctionRepository;
import lombok.RequiredArgsConstructor;
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


}
