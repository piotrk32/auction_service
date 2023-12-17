package com.example.auction_service.models.bid.dtos;

import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.address.dtos.AddressResponseDTO;
import com.example.auction_service.models.bid.Bid;

public class BidMapper {

    public static BidResponseDTO mapToBidResponseDTO(Bid bid) {
        return BidResponseDTO
                .builder()
                .bidId(bid.getId())
                .customerId(bid.getCustomer().getId())
                .customerEmail(bid.getCustomer().getEmail())
                .auctionId(bid.getAuction().getId())
                .bidValue(bid.getBidValue())
                .bidStatus(String.valueOf(bid.getBidStatus()))
                .isWinning(bid.getIsWinning())
                .build();
    }
}
