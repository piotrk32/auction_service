package com.example.auction_service.repositories;

import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    List<Item> findAllByProviderId(Long providerId);

    List<Item> findAllByAuctionId(Long auctionId);
    @Query("""
            SELECT i.provider.email
            FROM Item i
            WHERE i.id = :itemId
            """)
    String getProviderEmailByItemId(@Param("itemId") Long itemId);

    List<Item> findByItemNameContainingIgnoreCase(String nameFragment);

    List<Item> findAllByCustomerId(Long customerId);

//    List<Item> findByItemNameContainingIgnoreCaseAndAuction_IsActiveTrue(String nameFragment);

//    Page<Item> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}
