package com.example.auction_service.services.provider;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.models.provider.dtos.ProviderInputDTO;
import com.example.auction_service.models.user.User;
import com.example.auction_service.models.user.enums.Status;
import com.example.auction_service.repositories.ProviderRepository;
import com.example.auction_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final UserRepository userRepository;

    public Provider getProviderById(Long providerId) {
        return providerRepository.findById(providerId)
                .orElseThrow(() -> new EntityNotFoundException("Provider", "No provider found with id: " + providerId));
    }
    public Provider getProviderByOfferingId(Long offeringId) {
        return providerRepository.getProviderByOfferingId(offeringId)
                .orElseThrow(() -> new EntityNotFoundException("Provider", "No provider found with offering id: " + offeringId));
    }

    public Provider createProvider(Address address, ProviderInputDTO providerInputDTO, User user) {
        Provider provider = new Provider(
                address,
                providerInputDTO.getFirstName(),
                providerInputDTO.getLastName(),
                providerInputDTO.getBirthDate(),
                user.getEmail(),
                providerInputDTO.getPhoneNumber(),
                user.getAccessToken(),
                user.getRefreshToken(),
                user.getIdToken());
        userRepository.delete(user);
        return providerRepository.saveAndFlush(provider);
    }

    public void deleteProviderById(Long providerId) {
        Provider provider = getProviderById(providerId);
        provider.setStatus(Status.INACTIVE);
        providerRepository.saveAndFlush(provider);
    }

    public Provider updateProviderById(Long providerId, ProviderInputDTO providerInputDTO) {
        Provider provider = getProviderById(providerId);

        provider.setFirstName(providerInputDTO.getFirstName());
        provider.setLastName(providerInputDTO.getLastName());
        provider.setBirthDate(providerInputDTO.getBirthDate());
        provider.setPhoneNumber(providerInputDTO.getPhoneNumber());
        return providerRepository.saveAndFlush(provider);
    }

}
