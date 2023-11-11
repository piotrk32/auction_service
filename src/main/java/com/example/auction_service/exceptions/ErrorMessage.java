package com.example.auction_service.exceptions;

public record ErrorMessage(String fieldName, String error) {

    @Override
    public String toString() {
        return error;
    }

}
