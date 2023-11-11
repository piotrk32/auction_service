package com.example.auction_service.repositories;

import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.bid.enums.BidStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid> {
    Page<Bid> findAllByAuctionId(Long auctionId, PageRequest pageRequest);

    @Query("""
            SELECT b.customer.email
            FROM Bid b
            WHERE b.id = :bidId
            """)
    Optional<String> getCustomerEmailByBidId(@Param("bidId") Long bidId);

    Optional<Bid> findTopByAuctionIdAndBidStatusOrderByBidValueDesc(Long auctionId, BidStatus bidStatus);



}
