package com.example.auction_service.models.item.dtos;

import com.example.auction_service.models.item.Item;

public class ItemMapper {

    public static ItemResponseDTO mapToItemResponseDTO(Item item) {
        // Assuming you have getter methods for customer fields in your Item entity
        Long customerId = item.getCustomer() != null ? item.getCustomer().getId() : null;
        String customerFullName = item.getCustomer() != null
                ? item.getCustomer().getFirstName() + " " + item.getCustomer().getLastName()
                : "Not purchased";

        return ItemResponseDTO
                .builder()
                .itemId(item.getId())
                .providerId(item.getProvider().getId())
                .providerFullName(item.getProvider().getFirstName() + " " + item.getProvider().getLastName())
                .itemName(item.getItemName())
                .description(item.getDescription() != null ? item.getDescription() : "")
                .startingPrice(item.getStartingPrice())
                .imageUrl(item.getImageUrl())
                .isBuyNowActive(item.getIsBuyNowActive())
                .buyNowPrice(item.getBuyNowPrice())
                .isSold(item.getIsSold())
                .customerId(customerId) // Corrected field
                .customerFullName(customerFullName) // Corrected field
                .build();
    }
}
