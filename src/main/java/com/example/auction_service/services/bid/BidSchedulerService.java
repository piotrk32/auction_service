package com.example.auction_service.services.bid;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.enums.StatusAuction;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.repositories.AuctionRepository;
import com.example.auction_service.repositories.BidRepository;

import java.util.List;


@Service
public class BidSchedulerService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Scheduled(fixedRate = 600000) // Uruchamia co 10 minut
    public void updateWinningBidStatus() {
        // Pobierz wszystkie aktywne aukcje
        List<Auction> activeAuctions = auctionRepository.findAllByStatusAuction(StatusAuction.ACTIVE);

        for (Auction auction : activeAuctions) {
            updateAuctionWinningBid(auction);
        }
    }

    private void updateAuctionWinningBid(Auction auction) {
        // Pobierz wszystkie oferty dla danej aukcji
        List<Bid> bids = bidRepository.findAllByAuctionOrderByBidValueDesc(auction);

        if (!bids.isEmpty()) {
            // Ustaw najwyższą ofertę jako zwycięską
            Bid highestBid = bids.get(0);
            highestBid.setIsWinning(true);
            bidRepository.save(highestBid);

            // Ustaw pozostałe oferty jako przegrane
            bids.stream().skip(1).forEach(bid -> {
                bid.setIsWinning(false);
                bidRepository.save(bid);
            });
        }
    }
}
