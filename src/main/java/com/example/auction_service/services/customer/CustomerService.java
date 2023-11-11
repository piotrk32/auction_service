package com.example.auction_service.services.customer;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.repositories.CustomerRepository;
import com.example.auction_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "No customer found with id: " + customerId));
    }

    public Customer createCustomer(Address address, CustomerInputDTO customerInputDTO, User user) {
        Customer customer = new Customer(
                address,
                customerInputDTO.getFirstName(),
                customerInputDTO.getLastName(),
                customerInputDTO.getBirthDate(),
                user.getEmail(),
                customerInputDTO.getPhoneNumber(),
                user.getAccessToken(),
                user.getRefreshToken(),
                user.getIdToken());
        userRepository.delete(user);
        return customerRepository.saveAndFlush(customer);
    }





}
