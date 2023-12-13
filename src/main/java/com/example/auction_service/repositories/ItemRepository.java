package com.example.auction_service.repositories;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    Page<Item> findAllByProviderId(Long providerId, Pageable pageable);

    List<Item> findAllByAuctionId(Long auctionId);
    @Query("""
            SELECT i.provider.email
            FROM Item i
            WHERE i.id = :itemId
            """)
    String getProviderEmailByItemId(@Param("itemId") Long itemId);

    List<Item> findByItemNameContainingIgnoreCase(String nameFragment);

    List<Item> findAllByCustomerId(Long customerId);

    List<Item> findAllByAuction(Auction auction);

//    Page<Item> findAllByProviderIdAndIsActiveTrue(Long providerId, PageRequest pageRequest);

//    List<Item> findByItemNameContainingIgnoreCaseAndAuction_IsActiveTrue(String nameFragment);

//    Page<Item> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}
