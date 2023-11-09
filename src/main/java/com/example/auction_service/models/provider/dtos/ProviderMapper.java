package com.example.auction_service.models.provider.dtos;

import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.models.user.enums.Status;

public class ProviderMapper {

    public static ProviderResponseDTO mapToProviderResponseDTO(Provider provider) {
        return ProviderResponseDTO.builder()
                .providerId(provider.getId())
                .addressId(provider.getAddress().getId())
                .status(Status.valueOf(provider.getStatus().name()))
                .firstName(provider.getFirstName())
                .lastName(provider.getLastName())
                .birthDate(provider.getBirthDate())
                .email(provider.getEmail())
                .phoneNumber(provider.getPhoneNumber())
                .build();
    }

}
