package com.example.auction_service.repositories;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.enums.StatusAuction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    @Query("""
        SELECT o.provider.email
        FROM Auction o
        WHERE o.id = :auctionId
        """)
    Optional<String> getProviderEmailByAuctionId(@Param("auctionId") Long auctionId);

    Page<Auction> findAllByProviderIdAndStatusAuction(Long providerId, StatusAuction statusAuction, PageRequest pageRequest);

    @Query("SELECT DISTINCT a FROM Auction a JOIN a.bidList b WHERE b.customer.id = :customerId")
    Page<Auction> findAllByCustomerIdWithBids(@Param("customerId") Long customerId, Pageable pageable);

    List<Auction> findAllByStatusAuctionAndAuctionDateBefore(StatusAuction status, LocalDateTime dateTime);

    List<Auction> findAllByStatusAuctionAndAuctionDateEndBefore(StatusAuction statusAuction, LocalDateTime endDateTime);

    Page<Auction> findAllByProviderIdAndStatusAuction(Long providerId, StatusAuction statusAuction, Pageable pageable);

    List<Auction> findAllByStatusAuction(StatusAuction statusAuction);
}
