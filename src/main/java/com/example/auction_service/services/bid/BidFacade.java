package com.example.auction_service.services.bid;

import com.example.auction_service.models.bid.dtos.BidInputDTO;
import com.example.auction_service.models.bid.dtos.BidMapper;
import com.example.auction_service.models.bid.dtos.BidRequestDTO;
import com.example.auction_service.models.bid.dtos.BidResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidFacade {

    private final BidService bidService;
    // pozostałe pola

    public BidResponseDTO createBid(BidInputDTO bidInputDTO) {

        return bidService.createBid(bidInputDTO);
    }

    public Page<BidResponseDTO> getBidsByAuctionId(Long auctionId, BidRequestDTO bidRequestDTO) {
        return bidService.getBidsForAuction(auctionId, bidRequestDTO).map(BidMapper::mapToBidResponseDTO);
    }

}