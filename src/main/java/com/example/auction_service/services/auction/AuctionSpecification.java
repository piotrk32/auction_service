package com.example.auction_service.services.auction;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.provider.Provider;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class AuctionSpecification {

    public static Specification<Auction> auctionNameContains(String auctionName) {
        return (root, query, cb) -> auctionName == null ? null :
                cb.like(cb.lower(root.get("auctionName")), "%" + auctionName.toLowerCase() + "%");
    }
    public static Specification<Auction> auctionCategoryContains(String auctionCategory) {
        return (root, query, cb) -> auctionCategory == null ? null :
                cb.like(cb.lower(root.get("auctionCategory")), "%" + auctionCategory.toLowerCase() + "%");
    }

    public static Specification<Auction> priceGreaterThanOrEqual(String price) {
        return (root, query, cb) -> price == null ? null : cb.greaterThanOrEqualTo(root.get("price"),
                new BigDecimal(price));
    }

    public static Specification<Auction> priceLessThanOrEqual(String price) {
        return (root, query, cb) -> price == null ? null : cb.lessThanOrEqualTo(root.get("price"),
                new BigDecimal(price));
    }

    public static Specification<Auction> providerIdEquals(String providerId) {
        return (root, query, cb) -> {
            if (providerId == null) return null;
            Join<Auction, Provider> providerJoin = root.join("provider");
            return cb.equal(providerJoin.get("id"), Long.valueOf(providerId));
        };
    }
}
