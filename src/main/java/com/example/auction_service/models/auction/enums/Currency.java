package com.example.auction_service.models.auction.enums;

import java.util.Arrays;

public enum Currency {
    PLN,
    USD,
    EUR;

    public static boolean isValid(String value) {
        return Arrays.stream(Currency.values())
                .anyMatch(currency -> currency.name().equals(value));
    }

}