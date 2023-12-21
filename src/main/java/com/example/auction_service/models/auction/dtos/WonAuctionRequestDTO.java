package com.example.auction_service.models.auction.dtos;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Schema
@NoArgsConstructor
public class WonAuctionRequestDTO {

    @Schema(example = "0")
    @Min(value = 0, message = "Page index must not be less than zero.")
    private String page = "0";

    @Schema(example = "10")
    @Min(value = 1, message = "Page size must be at least one.")
    private String size = "10";

    @Schema(example = "price")
    private String sortParam = "price";

    @Schema(example = "DESC")
    private Sort.Direction direction = Sort.Direction.ASC;


}
