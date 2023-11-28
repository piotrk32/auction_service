package com.example.auction_service.models.bid.dtos;

public record BidResponseDTO(
        Long bidId,
        Long customerId,
        String customerEmail, // Opcjonalnie, dla lepszej czytelności
        Long auctionId,
        Double bidValue,
        String bidStatus,
        Boolean isWinning
) {}
