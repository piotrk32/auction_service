package com.example.auction_service.validators.item;

public class ItemValidators {
    public static final String ITEM_REGEX = "^[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ .,-]+$";

    public static boolean isValidItemName(String value) {
        return value.matches(ITEM_REGEX);
    }
}
