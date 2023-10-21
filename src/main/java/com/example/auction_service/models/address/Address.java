package com.example.auction_service.models.address;

import com.example.auction_service.models.basic.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends BasicEntity {

    String country;
    String city;
    String street;
    String zipCode;
    String buildingNumber;
    String apartmentNumber;
    Boolean isActive;

    public Address() {
        this.isActive = true;
    }

}
