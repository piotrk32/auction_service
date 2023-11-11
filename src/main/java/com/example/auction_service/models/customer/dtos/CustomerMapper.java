package com.example.auction_service.models.customer.dtos;

import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.models.user.enums.Status;

public class CustomerMapper {

    public static CustomerResponseDTO mapToCustomerResponseDTO(Customer customer) {
        return CustomerResponseDTO.builder()
                .customerId(customer.getId())
                .addressId(customer.getAddress().getId())
                .status(Status.valueOf(customer.getStatus().name()))
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthDate(customer.getBirthDate())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
