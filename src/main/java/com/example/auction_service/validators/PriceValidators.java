package com.example.auction_service.validators;

public class PriceValidators {

    public static final String PRICE_REGEX = "^[0-9]+([.,][0-9]{1,2})?$";

    public static boolean containsOnlyNumbersCommaOrDot(String value) {
        return value.matches("^[0-9,.]*$");
    }

    public static boolean isValidPriceFormat(String value) {
        return value.matches(PRICE_REGEX);
    }
}
