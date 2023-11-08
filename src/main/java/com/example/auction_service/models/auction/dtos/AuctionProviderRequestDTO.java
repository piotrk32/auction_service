package com.example.auction_service.models.auction.dtos;

import com.example.auction_service.validators.auctionpageparam.AuctionPageParam;
import com.example.auction_service.validators.pagedirection.PageDirection;
import com.example.auction_service.validators.pagesize.PageSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema
@NoArgsConstructor
public class AuctionProviderRequestDTO {
    @Schema(example = "0")
    @Min(value = 0)
    private String page = "0";
    @Schema(example = "25")
    @PageSize
    private String size = "10";
    @Schema(example = "price")
    @AuctionPageParam
    private String sortParam = "price";
    @Schema(example = "DESC")
    @PageDirection
    private String direction = "ASC";
}
