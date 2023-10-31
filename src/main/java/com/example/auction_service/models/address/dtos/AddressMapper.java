package com.example.auction_service.models.address.dtos;

import com.example.auction_service.models.address.Address;

public class AddressMapper {

    public static AddressResponseDTO mapToAddressResponseDTO(Address address) {
        return AddressResponseDTO.builder()
                .addressId(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .buildingNumber(address.getBuildingNumber())
                .apartmentNumber(address.getApartmentNumber())
                .isActive(address.getIsActive())
                .build();
    }

}
