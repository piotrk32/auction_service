package com.example.auction_service.services.customer;

import com.example.auction_service.exceptions.EntityNotFoundException;
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



}
