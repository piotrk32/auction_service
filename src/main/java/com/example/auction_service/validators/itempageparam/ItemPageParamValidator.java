package com.example.auction_service.validators.itempageparam;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ItemPageParamValidator implements ConstraintValidator<ItemPageParam, String> {

    private static final String[] ALLOWED_VALUES = {"id", "createdAt", "modifiedAt", "provider", "itemName",
            "description", "isActive", "startingPrice", "currency", "buyNowPrice"};

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