package com.example.auction_service.models.customer.dtos;

import com.example.auction_service.models.user.enums.Status;
import lombok.Builder;

import java.time.LocalDate;

public record CustomerResponseDTO(Long customerId,
                                  Long addressId,
                                  Status status,
                                  String firstName,
                                  String lastName,
                                  LocalDate birthDate,
                                  String email,
                                  String phoneNumber) {

    @Builder
    public CustomerResponseDTO {}

}
