package com.example.auction_service.exceptions.security;

public class GoogleTokenException extends RuntimeException {

    private final String entityName;

    public GoogleTokenException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }
    public String getEntityName() {
        return entityName;
    }

}
