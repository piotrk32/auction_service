package com.example.auction_service.models.bid.dtos;

import com.example.auction_service.validators.bidpageparam.BidPageParam;
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
public class BidRequestDTO {

    @Schema(example = "0")
    @Min(value = 0)
    private String page = "0";
    @Schema(example = "25")
    @PageSize
    private String size = "10";
    @Schema(example = "bidValue")
    @BidPageParam
    private String sortParam = "bidValue";
    @Schema(example = "DESC")
    @PageDirection
    private String direction = "ASC";


    // Konstruktor, gettery i settery
}