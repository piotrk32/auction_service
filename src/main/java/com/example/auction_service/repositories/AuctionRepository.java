package com.example.auction_service.repositories;

import com.example.auction_service.models.auction.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    @Query("""
        SELECT o.provider.email
        FROM Auction o
        WHERE o.id = :auctionId
        """)
    Optional<String> getProviderEmailByAuctionId(@Param("auctionId") Long auctionId);


    Page<Auction> findAllByProviderIdAndIsActiveTrue(Long providerId, PageRequest pageRequest);
}
