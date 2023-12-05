package com.example.auction_service.models.auction.dtos;

import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.validators.auction.ValidAuctionDates;
import com.example.auction_service.validators.item.ItemValidationGroups;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@ValidAuctionDates
public record AuctionInputDTO(
        @NotBlank(message = "Auction name field must be filled in.")
        @Length(min = 3, message = "Auction name must be at least 3 characters.")
        String auctionName,

        @NotBlank(message = "Auction description field must be filled in.")
        String description,

//        Integer duration,

        @NotNull(message = "Currency cannot be empty.")
        Currency currency,

        @NotNull(message = "Category cannot be empty.")
        String category,

        @NotNull(message = "Auction start date cannot be empty.")
        LocalDateTime auctionDate,

        @NotNull(message = "Starting price cannot be empty.")
        @Min(value = 0, message = "Starting price must not be less than or equal to zero.")
        Double price,

        @NotNull(message = "Auction end date cannot be empty.")
        LocalDateTime auctionDateEnd,

        Boolean isBuyNow,
        @NotNull(groups = ItemValidationGroups.BuyNowActive.class, message = "Buy now price cannot be empty if buy now is active.")
        Double buyNowPrice
) {
        // Klasy walidacyjne dla grupy BuyNowActive
}
