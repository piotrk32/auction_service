package com.example.auction_service.repositories;

import com.example.auction_service.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u.email
            FROM User u
            WHERE u.address.id = :addressId
            """)
    Optional<String> findUserEmailByAddressId(@Param("addressId") Long addressId);

    @Query("""
            SELECT u.email
            FROM User u
            WHERE u.id = :userId
            """)
    Optional<String> findUserEmailByUserId(@Param("userId") Long userId);

    Optional<User> findUserByEmail(String email);


}
