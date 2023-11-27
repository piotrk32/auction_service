package com.example.auction_service.models.auction;

import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.models.auction.enums.StatusAuction;
import com.example.auction_service.models.basic.BasicEntity;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.models.item.Item;
import com.example.auction_service.models.provider.Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @Column(nullable = false)
//    Boolean isActive;
    Integer duration;
    Double price;
    String category;

    LocalDateTime auctionDate;
    LocalDateTime auctionDateEnd;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_bid_id", referencedColumnName = "id")
    Bid currentBid;

    @Enumerated(EnumType.STRING)
    Currency currency;

    @Enumerated(EnumType.STRING)
    StatusAuction statusAuction;

    Boolean isBuyNow; // czy aukcja została zakończona przez Kup Teraz
    Double buyNowPrice; // cena Kup Teraz, jeśli jest aktywna
    @Column(nullable = false)
    Boolean isBuyNowCompleted = false;

    // Pole wskazujące na klienta, który zakończył aukcję poprzez Kup Teraz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buy_now_customer_id", referencedColumnName = "id")
    Customer buyNowCustomer;

    @OneToMany(mappedBy = "auction")
    private List<Item> items;



    public Auction(Provider provider, String auctionName, String category, String description, Integer duration,
                   Double price, Currency currency, Bid currentBid, LocalDateTime auctionDate,
                   LocalDateTime auctionDateEnd, Boolean isBuyNow, Double buyNowPrice, Boolean isBuyNowCompleted, @NotNull(message = "Auction activity cannot be empty.") String statusAuction) {
        this.provider = provider;
        this.auctionName = auctionName;
        this.category = category;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.currency = currency;
        this.currentBid = currentBid;
        this.auctionDate = auctionDate;
        this.auctionDateEnd = auctionDateEnd;
        this.isBuyNow = isBuyNow;
        this.buyNowPrice = buyNowPrice;
        this.isBuyNowCompleted = isBuyNowCompleted;

    }

}
