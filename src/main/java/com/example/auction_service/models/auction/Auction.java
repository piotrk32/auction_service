package com.example.auction_service.models.auction;

import com.example.auction_service.models.auction.enums.Currency;
import com.example.auction_service.models.basic.BasicEntity;
import com.example.auction_service.models.provider.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "offerings")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Auction extends BasicEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    Provider provider;

    String auctionName;
    String description;
    Boolean isActive;
    Integer duration;
    Double price;

    @Enumerated(EnumType.STRING)
    Currency currency;

    public Auction(Provider provider, String auctionName, String description, Integer duration, Double price, Currency currency) {
        this.provider = provider;
        this.auctionName = auctionName;
        this.description = description;
        this.isActive = true;
        this.duration = duration;
        this.price = price;
        this.currency = currency;
    }

}
