package com.example.auction_service.validators.auction;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AuctionDatesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAuctionDates {
    String message() default "Auction start date must be before auction end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
