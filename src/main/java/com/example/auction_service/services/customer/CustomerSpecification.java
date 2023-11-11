package com.example.auction_service.services.customer;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.models.user.enums.Status;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerSpecification {

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Specification<Customer> firstNameContains(String firstName) {
        return (root, query, cb) -> firstName == null ? null :
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Customer> lastNameContains(String lastName) {
        return (root, query, cb) -> lastName == null ? null :
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Customer> emailContains(String email) {
        return (root, query, cb) -> email == null ? null :
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Customer> phoneNumberContains(String phoneNumber) {
        return (root, query, cb) -> phoneNumber == null ? null :
                cb.like(cb.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
    }

    public static Specification<Customer> birthDateEquals(String birthDate) {
        return (root, query, cb) -> cb.equal(root.get("birthDate"), LocalDate.parse(birthDate, dateTimeFormatter));
    }

    public static Specification<Customer> statusEquals(String status) {
        return (root, query, cb) -> {
            Status enumStatus = Status.valueOf(status);
            return cb.equal(root.get("status"), enumStatus);
        };
    }

    public static Specification<Customer> customersByProviderId(Long providerId) {
        return (root, query, cb) -> {
            Subquery<Auction> subquery = query.subquery(Auction.class);
            Root<Auction> auctionRoot = subquery.from(Auction.class);
            Join<Auction, Provider> providerJoin = auctionRoot.join("provider", JoinType.INNER);
            subquery.select(auctionRoot);

            Join<Auction, Bid> bidJoin = auctionRoot.join("bidList", JoinType.INNER);
            Join<Bid, Customer> customerJoin = bidJoin.join("customer", JoinType.INNER);

            subquery.where(cb.equal(providerJoin.get("id"), providerId));

            // Select customers who have bids on auctions provided by the given provider.
            return cb.exists(subquery);
        };
    }
}
