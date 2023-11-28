package com.example.auction_service.models.bid.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BidUpdateDTO(
        @NotNull(message = "New bid value cannot be null.")
        @Min(value = 0, message = "New bid value must be greater than or equal to 0.")
        Double newBidValue
) {}
