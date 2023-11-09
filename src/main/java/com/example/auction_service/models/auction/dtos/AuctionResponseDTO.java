package com.example.auction_service.models.auction.dtos;

import lombok.Builder;

import java.time.LocalDateTime;


public record AuctionResponseDTO(Long auctionId,
                                 Long providerId,
                                 String providerFullName,
                                 String category,
                                 String auctionName,
                                 Integer duration,
                                 Double price,
                                 String currency,
                                 String description,
                                 String catergory,
                                 LocalDateTime auctionDate,
                                 LocalDateTime auctionDateEnd,
                                 Boolean isBuyNow,
                                 Double buyNowPrice,
                                 Boolean isActive) {

    @Builder
    public AuctionResponseDTO {}
}