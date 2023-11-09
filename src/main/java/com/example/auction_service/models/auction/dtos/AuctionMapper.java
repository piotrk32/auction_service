package com.example.auction_service.models.auction.dtos;

import com.example.auction_service.models.auction.Auction;

public class AuctionMapper {

    public static AuctionResponseDTO mapToAuctionResponseDTO(Auction auction) {
        return AuctionResponseDTO
                .builder()
                .auctionId(auction.getId())
                .providerId(auction.getProvider().getId())
                .providerFullName(auction.getProvider().getFirstName() + " " + auction.getProvider().getLastName())
                .category(auction.getCategory())
                .auctionName(auction.getAuctionName())
                .duration(auction.getDuration())
                .price(auction.getPrice())
                .currency(auction.getCurrency().name())
                .description(auction.getDescription() != null ? auction.getDescription() : "")
                .auctionDate(auction.getAuctionDate())
                .auctionDateEnd(auction.getAuctionDateEnd())
                .isBuyNow(auction.getIsBuyNow())
                .buyNowPrice(auction.getBuyNowPrice())
                .isActive(auction.getIsActive())
                .build();
    }

}
