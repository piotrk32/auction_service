package com.example.auction_service.validators.price;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<Price, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }
        if (value.isBlank()) {
            return false;
        }
        try {
            int number = Integer.parseInt(value);
            return (number >= 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}