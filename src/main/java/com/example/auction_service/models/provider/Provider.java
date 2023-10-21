package com.example.auction_service.models.provider;

import com.example.auction_service.models.address.Address;
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
@Table(name = "providers")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Provider extends User {

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    List<Offering> offerings;

    String specialization;

    public Provider(Address address,
                    String firstName,
                    String lastName,
                    LocalDate birthDate,
                    String email,
                    String phoneNumber,
                    String accessToken,
                    String refreshToken,
                    String idToken,
                    String specialization) {
        super(address, firstName, lastName, birthDate, email, phoneNumber, accessToken, refreshToken, idToken);
        this.specialization = specialization;
    }

}
