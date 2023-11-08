package com.example.auction_service.services.auction;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.repositories.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
