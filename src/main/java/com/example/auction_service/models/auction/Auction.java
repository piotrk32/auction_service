package com.example.auction_service.models.auction;

import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.models.basic.BasicEntity;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.provider.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "auctions")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Auction extends BasicEntity {

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    List<Bid> bidList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    Provider provider;

    String auctionName;
    String description;
    Boolean isActive;
    Integer duration;
    Double price;

    LocalDateTime auctionDate;
    LocalDateTime auctionDateEnd;

    //TODO CURRENT BID
    Double currentBid;

    @Enumerated(EnumType.STRING)
    Currency currency;

    Boolean isBuyNow; // czy aukcja została zakończona przez Kup Teraz
    Double buyNowPrice; // cena Kup Teraz, jeśli jest aktywna

    public Auction(Provider provider, String auctionName, String description, Integer duration, Double price, Currency currency, Double currentBid, LocalDateTime auctionDate,
                   LocalDateTime auctionDateEnd, Boolean isBuyNow, Double buyNowPrice) {
        this.provider = provider;
        this.auctionName = auctionName;
        this.description = description;
        this.isActive = true;
        this.duration = duration;
        this.price = price;
        this.currency = currency;
        this.currentBid = currentBid;
        this.auctionDate = auctionDate;
        this.auctionDateEnd = auctionDateEnd;
        this.isBuyNow = isBuyNow;
        this.buyNowPrice = buyNowPrice;

    }

}
