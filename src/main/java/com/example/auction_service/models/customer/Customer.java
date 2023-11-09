package com.example.auction_service.models.customer;

import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

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

    //TODO connection with items bought in auction

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    List<Bid> bidList;

}
