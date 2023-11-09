package com.example.auction_service.models.auction.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Schema
@NoArgsConstructor
public class AuctionRequestDTO {

    @Schema(example = "0")
    @Min(value = 0, message = "Page index must not be less than zero.")
    private String page = "0";

    @Schema(example = "10")
    @Min(value = 1, message = "Page size must be at least one.")
    private String size = "10";

    @Schema(example = "price")
    private String sortParam = "price";

    @Schema(example = "DESC")
    private String direction = "ASC";

    @Schema(example = "Antique Vase")
    @Length(min = 3, message = "Auction name search must be at least 3 characters long.")
    private String auctionNameSearch = null;

    @Schema(example = "Cars")
    @Length(min = 3, message = "Auction category search must be at least 3 characters long.")
    private String auctionCategorySearch = null;

    @Schema(example = "100")
    @Min(value = 0, message = "Minimum price must not be less than zero.")
    private String priceFrom = null;

    @Schema(example = "10000")
    @Min(value = 1, message = "Maximum price must be at least one.")
    private String priceTo = null;

    @Schema(example = "123")
    @Min(value = 1, message = "Provider ID must be at least one.")
    private String providerId = null;

}
