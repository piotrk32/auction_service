package com.example.auction_service.models.customer;

import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends User {

    public Customer(Address address,
                    String firstName,
                    String lastName,
                    LocalDate birthDate,
                    String email,
                    String phoneNumber,
                    String accessToken,
                    String refreshToken,
                    String idToken) {
        super(address, firstName, lastName, birthDate, email, phoneNumber, accessToken, refreshToken, idToken);
    }

//    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
//    List<Reservation> reservations;

}
