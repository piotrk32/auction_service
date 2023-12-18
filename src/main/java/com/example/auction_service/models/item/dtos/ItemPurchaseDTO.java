package com.example.auction_service.models.item.dtos;

import lombok.Builder;

public record ItemPurchaseDTO(
        Long itemId,
        Long providerId,
        String itemName,
        Double buyNowPrice,
        Boolean isBuyNowActive,
        Boolean isSold) {

    @Builder
    public ItemPurchaseDTO {}

}
