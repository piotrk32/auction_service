package com.example.auction_service.services.auction;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.enums.StatusAuction;
import com.example.auction_service.repositories.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionSchedulerService {

    @Autowired
    private AuctionRepository auctionRepository;

    // Uruchom co 10 min
    @Scheduled(fixedRate = 600000)
    public void startScheduledAuctions() {
        LocalDateTime now = LocalDateTime.now();
        List<Auction> auctionsToStart = auctionRepository.findAllByStatusAuctionAndAuctionDateBefore(StatusAuction.NOT_STARTED, now);

        for (Auction auction : auctionsToStart) {
            auction.setStatusAuction(StatusAuction.ACTIVE);
            auctionRepository.save(auction);
        }
    }

    @Scheduled(fixedRate = 600000) // Uruchamia co 10 min
    public void updateAuctionStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<Auction> auctionsToFinish = auctionRepository.findAllByStatusAuctionAndAuctionDateEndBefore(StatusAuction.ACTIVE, now);

        for (Auction auction : auctionsToFinish) {
            auction.setStatusAuction(StatusAuction.FINISHED);
            auctionRepository.save(auction);
        }
    }
}