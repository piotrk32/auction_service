package com.example.auction_service.validators;

public class NameValidators {

    public static final String NAME_REGEX = "^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*$";

    public static boolean containsLettersAndSpaces(String value) {
        return value.chars()
                .allMatch(character -> Character.isLetter(character) || Character.isSpaceChar(character));
    }

    public static boolean containsLettersDigitsAndSpaces(String value) {
        return value.chars()
                .allMatch(character -> Character.isLetterOrDigit(character) || Character.isSpaceChar(character));
    }

}
