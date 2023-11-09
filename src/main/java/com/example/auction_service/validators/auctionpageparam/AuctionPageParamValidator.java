package com.example.auction_service.validators.auctionpageparam;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuctionPageParamValidator implements ConstraintValidator<AuctionPageParam, String> {

    private static final String[] ALLOWED_VALUES = {"id", "createdAt", "modifiedAt", "provider", "auctionName",
            "description", "isActive", "duration", "price", "currency, category, auctionDate, auctionDateEnd, isBuyNow, buyNowPrice"};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }
        for (String allowedValue : ALLOWED_VALUES) {
            if (allowedValue.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
