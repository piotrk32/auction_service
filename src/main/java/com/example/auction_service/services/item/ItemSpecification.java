package com.example.auction_service.services.item;

import com.example.auction_service.models.item.Item;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpecification {

    public static Specification<Item> itemNameContains(String itemName) {
        return (root, query, criteriaBuilder) -> {
            if (itemName == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // zawsze prawda = bez warunku
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("itemName")), "%" + itemName.toLowerCase() + "%");
        };
    }

    public static Specification<Item> itemCategoryContains(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + category.toLowerCase() + "%");
        };
    }

    public static Specification<Item> priceGreaterThanOrEqual(Double priceFrom) {
        return (root, query, criteriaBuilder) -> {
            if (priceFrom == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("startingPrice"), priceFrom);
        };
    }

    public static Specification<Item> priceLessThanOrEqual(Double priceTo) {
        return (root, query, criteriaBuilder) -> {
            if (priceTo == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("startingPrice"), priceTo);
        };
    }

    public static Specification<Item> isBuyNowActive(Boolean isBuyNowActive) {
        return (root, query, criteriaBuilder) -> {
            if (isBuyNowActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("isBuyNowActive"), isBuyNowActive);
        };
    }

    public static Specification<Item> isSold(Boolean isSold) {
        return (root, query, criteriaBuilder) -> {
            if (isSold == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("isSold"), isSold);
        };
    }
}
