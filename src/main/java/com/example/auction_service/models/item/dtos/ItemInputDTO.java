package com.example.auction_service.models.item.dtos;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemInputDTO(
        @NotNull(message = "Provider id cannot be empty.")
        Long providerId,
        @NotBlank(message = "Item name field must be filled in.")
        String itemName,
        String description,
        @NotNull(message = "Starting price cannot be empty.")
        @Min(value = 0, message = "Starting price must not be less than or equal to zero.")
        Double startingPrice,
        String imageUrl,
        Boolean isBuyNowActive,
        Boolean isSold,
        @Min(value = 0, message = "Buy now price must not be less than or equal to zero.")
        Double buyNowPrice ){

}

