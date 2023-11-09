package com.example.auction_service.exceptions.user;

public class UserAlreadyExistsException extends RuntimeException {

    private final String fieldName;

    public UserAlreadyExistsException(String entityName, String message) {
        super(message);
        this.fieldName = entityName;
    }
    public String getFieldName() {
        return fieldName;
    }

}
