package com.example.auction_service.models.auction.dtos;

import com.example.auction_service.models.auction.enums.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record AuctionInputDTO(
        @NotNull(message = "Provider id cannot be empty.")
        Long providerId,

        @NotBlank(message = "Auction name field must be filled in.")
        @Length(min = 3, message = "Auction name must be at least 3 characters.")
        String auctionName,

        @NotBlank(message = "Auction description field must be filled in.")
        String description,

        @NotNull(message = "Duration cannot be empty.")
        @Min(value = 0, message = "Duration must not be less than or equal to zero.")
        Integer duration,

        @NotNull(message = "Price cannot be empty.")
        @Min(value = 0, message = "Price must not be less than or equal to zero.")
        Double price,

        @NotNull(message = "Currency cannot be empty.")
        Currency currency,

        @NotNull(message = "Auction start date cannot be empty.")
        LocalDateTime auctionDate,
        @NotNull(message = "Auction end date cannot be empty.")
        LocalDateTime auctionDateEnd,

        Boolean isBuyNow,

        Double buyNowPrice,

        Boolean isActive
) {
}
