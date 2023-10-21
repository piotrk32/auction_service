package com.example.auction_service.models.user;


import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.basic.BasicEntity;
import com.example.auction_service.models.user.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BasicEntity implements UserDetails {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    Address address;

    String firstName;
    String lastName;
    LocalDate birthDate;
    String email;
    String phoneNumber;
    String accessToken;
    String refreshToken;
    String idToken;

    @Enumerated(EnumType.STRING)
    Status status;

    public User(String email,
                String accessToken,
                String refreshToken,
                String idToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
        this.status = Status.REGISTRATION_INCOMPLETE;
    }

    public User(Address address,
                String firstName,
                String lastName,
                LocalDate birthDate,
                String email,
                String phoneNumber,
                String accessToken,
                String refreshToken,
                String idToken) {
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
        this.status = Status.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (this instanceof Customer) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        } else if (this instanceof Provider) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PROVIDER"));
        }
        if (this.status.equals(Status.REGISTRATION_INCOMPLETE)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_INCOMPLETE_REGISTRATION"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == Status.ACTIVE || status == Status.REGISTRATION_INCOMPLETE;
    }

}
