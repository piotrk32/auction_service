package com.example.auction_service.validators.price;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PriceValidator.class)
public @interface Price {

    String message() default "The price must be a number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

