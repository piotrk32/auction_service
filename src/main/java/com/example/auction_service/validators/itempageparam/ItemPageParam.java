package com.example.auction_service.validators.itempageparam;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ItemPageParamValidator.class)
public @interface ItemPageParam {

    String message() default "Sorting parameter must be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

