package com.example.auction_service.models.bid;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.models.basic.BasicEntity;
import com.example.auction_service.models.bid.enums.BidStatus;
import com.example.auction_service.models.customer.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "bids")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bid extends BasicEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    Auction auction;

    Double bidValue;

    @Enumerated(EnumType.STRING)
    BidStatus bidStatus;

    // Pole wskazujące, czy oferta jest aktualnie zwycięska
    @Column(nullable = false)
    Boolean isWinning = false;

    public Bid(Customer customer, Auction auction,  Double price, Currency currency, Double bidValue) {
        this.customer = customer;
        this.auction = auction;
        this.bidValue = bidValue;
        this.bidStatus = BidStatus.ACTIVE;
    }






}
