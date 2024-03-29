package com.example.auction_service.repositories;

import com.example.auction_service.models.provider.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query("""
            SELECT p
            FROM Provider p
            WHERE p.id = (SELECT o.provider.id FROM Auction o WHERE o.id =  :auctionId)
            """)
    Optional<Provider> getProviderByAuctionId(@Param("auctionId") Long auctionId);

}
