package com.example.auction_service.models.address.dtos;

import lombok.Builder;

public record AddressResponseDTO(Long addressId,
                                 String country,
                                 String city,
                                 String street,
                                 String zipCode,
                                 String buildingNumber,
                                 String apartmentNumber,
                                 Boolean isActive) {

    @Builder
    public AddressResponseDTO {}

}
