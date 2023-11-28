package com.example.auction_service.models.bid.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BidInputDTO(
        @NotNull(message = "Customer ID cannot be null.")
        Long customerId,

        @NotNull(message = "Auction ID cannot be null.")
        Long auctionId,

        @NotNull(message = "Bid value cannot be null.")
        @Min(value = 0, message = "Bid value must be greater than or equal to 0.")
        Double bidValue
) {}