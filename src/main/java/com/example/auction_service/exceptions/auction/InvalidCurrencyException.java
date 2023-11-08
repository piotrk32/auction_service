package com.example.auction_service.exceptions.auction;

public class InvalidCurrencyException extends IllegalArgumentException {

    private final String fieldName;

    public InvalidCurrencyException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
