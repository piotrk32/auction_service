package com.example.auction_service.models.item.dtos;

import lombok.Builder;

public record ItemResponseDTO(
        Long itemId,
        Long providerId,
        String providerFullName,
        String itemName,
        String description,
        Double startingPrice,
        String imageUrl,
        Boolean isBuyNowActive,
        Double buyNowPrice,
        Boolean isSold) {

    @Builder
    public ItemResponseDTO {}

}