package com.example.auction_service.services.customer;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.enums.StatusAuction;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.bid.enums.BidStatus;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.models.customer.dtos.CustomerInputDTO;
import com.example.auction_service.models.customer.dtos.CustomerRequestDTO;
import com.example.auction_service.models.user.User;
import com.example.auction_service.models.user.enums.Status;
import com.example.auction_service.repositories.AuctionRepository;
import com.example.auction_service.repositories.BidRepository;
import com.example.auction_service.repositories.CustomerRepository;
import com.example.auction_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "No customer found with id: " + customerId));
    }

    public Customer createCustomer(Address address, CustomerInputDTO customerInputDTO, User user) {
        Customer customer = new Customer(
                address,
                customerInputDTO.getFirstName(),
                customerInputDTO.getLastName(),
                customerInputDTO.getBirthDate(),
                user.getEmail(),
                customerInputDTO.getPhoneNumber(),
                user.getAccessToken(),
                user.getRefreshToken(),
                user.getIdToken());
        userRepository.delete(user);
        return customerRepository.saveAndFlush(customer);
    }

    public void deleteCustomerById(Long customerId) {
        Customer customer = getCustomerById(customerId);
        customer.setStatus(Status.INACTIVE);
        customerRepository.saveAndFlush(customer);
    }

    public Customer updateCustomerById(Long customerId, CustomerInputDTO customerInputDTO) {
        Customer customer = getCustomerById(customerId);
        customer.setFirstName(customerInputDTO.getFirstName());
        customer.setLastName(customerInputDTO.getLastName());
        customer.setBirthDate(customerInputDTO.getBirthDate());
        customer.setPhoneNumber(customerInputDTO.getPhoneNumber());

        return customerRepository.saveAndFlush(customer);
    }

    public Page<Customer> getAllCustomersIdByProvider(Long providerId, CustomerRequestDTO customerRequestDTO) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(customerRequestDTO.getPage()),
                Integer.parseInt(customerRequestDTO.getSize()),
                Sort.Direction.valueOf(customerRequestDTO.getDirection()),
                customerRequestDTO.getSortParam());

        Specification<Customer> spec = Specification.where(null);

        if (customerRequestDTO.getFirstName() != null) {
            spec = spec.and(CustomerSpecification.firstNameContains(customerRequestDTO.getFirstName()));
        }
        if (customerRequestDTO.getLastName() != null) {
            spec = spec.and(CustomerSpecification.lastNameContains(customerRequestDTO.getLastName()));
        }
        if (customerRequestDTO.getEmail() != null) {
            spec = spec.and(CustomerSpecification.emailContains(customerRequestDTO.getEmail()));
        }
        if (customerRequestDTO.getPhoneNumber() != null) {
            spec = spec.and(CustomerSpecification.phoneNumberContains(customerRequestDTO.getPhoneNumber()));
        }
        if (customerRequestDTO.getBirthDate() != null) {
            spec = spec.and(CustomerSpecification.birthDateEquals(customerRequestDTO.getBirthDate()));
        }
        if (customerRequestDTO.getStatus() != null) {
            spec = spec.and(CustomerSpecification.statusEquals(customerRequestDTO.getStatus()));
        }
        if (providerId != null) {
            spec = spec.and(CustomerSpecification.customersByProviderId(providerId));
        }

        return customerRepository.findAll(spec, pageRequest);
    }

    public Page<Auction> getParticipatedAuctions(Long customerId, Pageable pageable) {
        // Wyszukujemy wszystkie aukcje, w których klient złożył ofertę
        return auctionRepository.findAllByCustomerIdWithBids(customerId, pageable);
    }

    public void registerPurchase(Long customerId, Long auctionId) {
        // Weryfikujemy istnienie klienta i aukcji
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "No customer found with id: " + customerId));
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction", "No auction found with id: " + auctionId));

        // Zaktualizuj stan aukcji, aby odzwierciedlić fakt dokonania zakupu
        auction.setStatusAuction(StatusAuction.FINISHED);
        auction.setIsBuyNowCompleted(true);
        auction.setBuyNowCustomer(customer);

        // Jeśli istnieje, zaktualizuj aktualną wygraną ofertę
        Bid winningBid = bidRepository.findTopByAuctionIdAndBidStatusOrderByBidValueDesc(auctionId, BidStatus.BOOKED)
                .orElse(null);
        if (winningBid != null) {
            winningBid.setBidStatus(BidStatus.WON);
            bidRepository.save(winningBid);
        }

        // Zapisz zmiany w aukcji
        auctionRepository.saveAndFlush(auction);
    }
}






