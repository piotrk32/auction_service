package com.example.auction_service.models.item.dtos;

import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.validators.item.ItemValidationGroups;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import static com.example.auction_service.validators.item.ItemValidators.ITEM_REGEX;
//import static com.example.auction_service.validators.item.PriceValidators.PRICE_REGEX;

//@Validated(ItemValidationGroups.BuyNowCheck.class)
public record ItemUpdateDTO(
        @NotBlank(message = "Item name field must be filled in.")
        @Length(min = 1, max = 255, message = "Item name must be between 1 and 255 characters.")
        @Pattern(regexp = ITEM_REGEX, message = "Item name must contain letters, numbers and -,. .")
        String itemName,

        @Length(max = 1000, message = "Description must be less than or equal to 1000 characters.")
        @Length(min = 1, max = 255, message = "Item description must be between 1 and 255 characters.")
        @Pattern(regexp = ITEM_REGEX, message = "Item description must contain letters, numbers and -,. .")
        String description,

        @NotNull(message = "Starting price cannot be empty.")
        @Min(value = 0, message = "Starting price must not be less than or equal to zero.")
        Double startingPrice,

        @Pattern(regexp = "^(http[s]?:\\/\\/)?[\\w\\S]*(\\.png|\\.jpg|\\.jpeg)$", message = "Invalid image URL format.")
        String imageUrl,

        Boolean isBuyNowActive,

        Boolean isSold,

        @Min(value = 0, message = "Buy Now price must not be less than or equal to zero.")
        Double buyNowPrice,
        String currency



) {
}
