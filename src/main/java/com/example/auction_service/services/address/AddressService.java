package com.example.auction_service.services.address;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.address.dtos.AddressUpdateDTO;
import com.example.auction_service.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address createEmptyAddress() {
        return addressRepository.saveAndFlush(new Address());
    }

    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address", "No address found with id: " + addressId));
    }

    public Address updateAddress(Long addressId, AddressUpdateDTO addressUpdateDTO) {
        Address address = getAddressById(addressId);

        address.setCountry(addressUpdateDTO.country());
        address.setCity(addressUpdateDTO.city());
        address.setStreet(addressUpdateDTO.street());
        address.setZipCode(addressUpdateDTO.zipCode());
        address.setBuildingNumber(addressUpdateDTO.buildingNumber());
        address.setApartmentNumber(addressUpdateDTO.apartmentNumber());

        return addressRepository.saveAndFlush(address);
    }

    public void deleteAddressById(Long addressId) {
        Address address = getAddressById(addressId);
        address.setIsActive(false);
        addressRepository.saveAndFlush(address);
    }
}
