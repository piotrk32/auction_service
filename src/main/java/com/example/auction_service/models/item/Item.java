package com.example.auction_service.models.item;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.basic.BasicEntity;
import com.example.auction_service.models.provider.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "items")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item extends BasicEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;


    String itemName;
    String description;
    Double startingPrice;
    String imageUrl; // zdjecie

    Double buyNowPrice; // cena Kup Teraz
    Boolean isBuyNowActive; // czy opcja Kup Teraz jest aktywna




    public Item(Provider provider, String itemName, String description, Double startingPrice,
                String imageUrl, Boolean isBuyNowActive, Double buyNowPrice) {
        this.provider = provider;
        this.itemName = itemName;
        this.description = description;
        this.startingPrice = startingPrice;
        this.imageUrl = imageUrl;
        this.isBuyNowActive = isBuyNowActive;
        this.buyNowPrice = buyNowPrice;

    }




}
