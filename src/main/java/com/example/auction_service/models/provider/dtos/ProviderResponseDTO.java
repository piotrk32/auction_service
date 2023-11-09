package com.example.auction_service.models.provider.dtos;

import com.example.auction_service.models.user.enums.Status;
import lombok.Builder;

import java.time.LocalDate;

public record ProviderResponseDTO(Long providerId,
                                  Long addressId,
                                  Status status,
                                  String firstName,
                                  String lastName,
                                  LocalDate birthDate,
                                  String email,
                                  String phoneNumber
                                  ) {

    @Builder
    public ProviderResponseDTO {}

}
