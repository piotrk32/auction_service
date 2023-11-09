package com.example.auction_service.models.user.dtos;

import com.example.auction_service.validators.NameValidators;
import com.example.auction_service.validators.age.Adult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
public class UserInputDTO {

    @NotBlank(message = "First name field must be filled in.")
    @Length(min = 2, max = 255, message = "First name must be between 2 and 255 characters.")
    @Pattern(regexp = NameValidators.NAME_REGEX, message = "First name must contain only letters.")
    private String firstName;

    @NotBlank(message = "Last name field must be filled in.")
    @Length(min = 2, max = 255, message = "Last name must be between 2 and 255 characters.")
    @Pattern(regexp = NameValidators.NAME_REGEX, message = "Last name must contain only letters.")
    private String lastName;

    @NotNull(message = "Birth date field must be filled in.")
    @Adult
    private LocalDate birthDate;

    @NotBlank(message = "Phone number field must be filled in.")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits.")
    @Length(min = 7, max = 15, message = "Phone number must be between 7 and 15 digits.")
    private String phoneNumber;

}
