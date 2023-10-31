package com.example.auction_service.services.address;

import com.example.auction_service.models.address.dtos.AddressResponseDTO;
import com.example.auction_service.models.address.dtos.AddressUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.auction_service.models.address.dtos.AddressMapper.mapToAddressResponseDTO;

@Component
@RequiredArgsConstructor
public class AddressFacade {

    private final AddressService addressService;

    public AddressResponseDTO updateAddress(Long addressId, AddressUpdateDTO addressUpdateDTO) {
        return mapToAddressResponseDTO(addressService.updateAddress(addressId, addressUpdateDTO));
    }

    public AddressResponseDTO getAddressById(Long addressId) {
        return mapToAddressResponseDTO(addressService.getAddressById(addressId));
    }

    public void deleteAddress(Long addressId) {
        addressService.deleteAddressById(addressId);
    }

}
