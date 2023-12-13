package com.example.auction_service.security.services;
import com.example.auction_service.services.auction.AuctionService;
import com.example.auction_service.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FineGrainServices {
    private final UserService userService;
    private final AuctionService auctionService;

    public String getUserEmail() {
        return (SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserIdByUserEmail(email);
    }

    public boolean compareSecurityEmailAndEmailByUserId(Long userId) {
        String userIdEmail = userService.getUserEmailByUserId(userId);
        return getUserEmail().equals(userIdEmail);
    }

    public boolean compareSecurityEmailAndEmailByAddressId(Long addressId) {
        String addressOwnerEmail = userService.getUserEmailByAddressId(addressId);
        return getUserEmail().equals(addressOwnerEmail);
    }

    public boolean compareSecurityEmailAndEmailByAuctionId(Long auctionId) {
        String reservationCustomerEmail = auctionService.getProviderEmailByAuctionId(auctionId);
        return getUserEmail().equals(reservationCustomerEmail);
    }


}
