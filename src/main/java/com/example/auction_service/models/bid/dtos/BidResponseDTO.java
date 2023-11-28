package com.example.auction_service.models.bid.dtos;

import lombok.Builder;

public record BidResponseDTO(
        Long bidId,
        Long customerId,
        String customerEmail,
        Long auctionId,
        Double bidValue,
        String bidStatus,
        Boolean isWinning) {

    @Builder
    public BidResponseDTO {}

}
