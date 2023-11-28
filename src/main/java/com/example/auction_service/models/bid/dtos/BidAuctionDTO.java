package com.example.auction_service.models.bid.dtos;

import java.util.List;

public record BidAuctionDTO(
        Long auctionId,
        List<BidResponseDTO> bids
) {}
