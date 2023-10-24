package com.example.auction_service.repositories;

import com.example.auction_service.models.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
