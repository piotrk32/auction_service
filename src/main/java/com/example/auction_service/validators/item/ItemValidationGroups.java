package com.example.auction_service.validators.item;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

public class ItemValidationGroups {

    // Grupa walidacyjna dla warunku aktywnego "Buy Now"
    public interface BuyNowActive {}

    // Sekwencja grup - Default jest sprawdzane zawsze, BuyNowActive tylko gdy isBuyNowActive jest true
    @GroupSequence({Default.class, BuyNowActive.class})
    public interface BuyNowCheck {}

}
