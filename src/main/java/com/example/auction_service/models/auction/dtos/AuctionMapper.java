package com.example.auction_service.models.auction.dtos;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class AuctionMapper {

    public static AuctionResponseDTO mapToAuctionResponseDTO(Auction auction) {
        // Mapowanie listy ID przedmiotów
        List<Long> itemIds = auction.getItems().stream()
                .map(Item::getId)
                .collect(Collectors.toList());

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
                .statusAuction(auction.getStatusAuction().name())
                .currentBid(auction.getCurrentBid().getId())
                .itemIds(itemIds) // Dodanie listy ID przedmiotów
                .build();
    }
}
