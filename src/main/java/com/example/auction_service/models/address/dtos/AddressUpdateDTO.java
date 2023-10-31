package com.example.auction_service.models.address.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import static com.example.auction_service.validators.NameValidators.NAME_REGEX;

public record AddressUpdateDTO(@NotBlank(message = "Country name field must be filled in.")
                               @Length(min = 4, max = 255, message = "Country name must be between 4 and 255 characters.")
                               @Pattern(regexp = NAME_REGEX, message = "Country name must contain only letters.")
                               String country,
                               @NotBlank(message = "City name field must be filled in.")
                               @Length(min = 1, max = 255, message = "City name must be between 1 and 255 characters.")
                               @Pattern(regexp = NAME_REGEX, message = "City name must contain only letters.")
                               String city,
                               @NotBlank(message = "Street field must be filled in.")
                               @Length(min = 1, max = 255, message = "Street must be between 1 and 255 characters.")
                               String street,
                               @NotBlank(message = "Zip code field must be filled in.")
                               @Length(min = 1, max = 10, message = "Zip code must be between 1 and 10 characters.")
                               String zipCode,
                               @NotBlank(message = "Building number field must be filled in.")
                               @Length(min = 1, max = 10, message = "Building number must be between 1 and 10 characters.")
                               String buildingNumber,
                               @NotBlank(message = "Building number field must be filled in.")
                               @Length(min = 1, max = 10, message = "Building number must be between 1 and 10 characters.")
                               String apartmentNumber) {
}