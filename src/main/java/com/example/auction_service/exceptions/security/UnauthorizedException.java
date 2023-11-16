package com.example.auction_service.exceptions.security;

public class UnauthorizedException extends RuntimeException {

    private final String entityName;

    public UnauthorizedException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }
    public String getEntityName() {
        return entityName;
    }

}
