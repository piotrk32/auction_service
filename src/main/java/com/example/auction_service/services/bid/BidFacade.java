package com.example.auction_service.services.bid;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.bid.dtos.BidInputDTO;
import com.example.auction_service.models.bid.dtos.BidMapper;
import com.example.auction_service.models.bid.dtos.BidResponseDTO;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.services.auction.AuctionService;
import com.example.auction_service.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.auction_service.models.bid.dtos.BidMapper.mapToBidResponseDTO;

@Component
@RequiredArgsConstructor
public class BidFacade {

    private final BidService bidService;
    // pozostałe pola

    public BidResponseDTO createBid(BidInputDTO bidInputDTO) {

        return bidService.createBid(bidInputDTO);
    }
}