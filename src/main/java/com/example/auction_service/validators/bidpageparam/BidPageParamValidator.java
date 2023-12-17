package com.example.auction_service.validators.bidpageparam;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BidPageParamValidator implements ConstraintValidator<BidPageParam, String> {

    private static final String[] ALLOWED_VALUES = {"bidValue", "isWinning", "bidStatus", "auctionId", "customerEmail", "customerId", "bidId"};

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