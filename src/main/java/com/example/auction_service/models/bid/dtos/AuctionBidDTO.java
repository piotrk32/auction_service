package com.example.auction_service.models.bid.dtos;

import java.util.List;

public record AuctionBidDTO(
        Long auctionId,
        String auctionName,
        List<BidResponseDTO> bids
) {}

