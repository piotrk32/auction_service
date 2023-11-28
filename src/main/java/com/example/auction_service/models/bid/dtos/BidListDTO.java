package com.example.auction_service.models.bid.dtos;

import java.util.List;

public record BidListDTO(
        List<BidResponseDTO> bids
) {}
