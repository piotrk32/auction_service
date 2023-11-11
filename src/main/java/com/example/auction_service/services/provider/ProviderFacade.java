package com.example.auction_service.services.provider;

import com.example.auction_service.exceptions.user.UserAlreadyExistsException;
import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.customer.dtos.CustomerMapper;
import com.example.auction_service.models.customer.dtos.CustomerRequestDTO;
import com.example.auction_service.models.customer.dtos.CustomerResponseDTO;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.models.provider.dtos.ProviderInputDTO;
import com.example.auction_service.models.provider.dtos.ProviderResponseDTO;
import com.example.auction_service.models.user.User;
import com.example.auction_service.models.user.enums.Status;
import com.example.auction_service.services.address.AddressService;
import com.example.auction_service.services.customer.CustomerService;
import com.example.auction_service.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.example.auction_service.models.provider.dtos.ProviderMapper.mapToProviderResponseDTO;

@Component
@RequiredArgsConstructor
public class ProviderFacade {

    private final UserService userService;
    private final AddressService addressService;
    private final ProviderService providerService;
    private final CustomerService customerService;


    public ProviderResponseDTO createProvider(ProviderInputDTO providerInputDTO, String email) {
        User user = userService.getUserByEmail(email);
        if (user.getStatus() == Status.REGISTRATION_INCOMPLETE) {
            Address emptyAddress = addressService.createEmptyAddress();
            Provider provider = providerService.createProvider(emptyAddress, providerInputDTO, user);

            return mapToProviderResponseDTO(provider);
        }
        throw new UserAlreadyExistsException("email", "User with this email already exists.");
    }

    public void deleteProviderById(Long providerId) {
        providerService.deleteProviderById(providerId);
    }

    public ProviderResponseDTO updateProviderById(Long id, ProviderInputDTO providerInputDTO) {
        Provider provider = providerService.updateProviderById(id, providerInputDTO);
        return mapToProviderResponseDTO(provider);
    }

    public ProviderResponseDTO getProviderById(Long providerId) {
        return mapToProviderResponseDTO(providerService.getProviderById(providerId));
    }

    public ProviderResponseDTO getProviderByAuctionId(Long auctionId) {
        return mapToProviderResponseDTO(providerService.getProviderByAuctionId(auctionId));
    }

    //TODO CustomerService and Facade

    public Page<CustomerResponseDTO> getCustomersByProviderId(Long providerId, CustomerRequestDTO customerRequestDTO) {
        return customerService.getAllCustomersIdByProvider(providerId, customerRequestDTO).map(CustomerMapper::mapToCustomerResponseDTO);
    }



}
