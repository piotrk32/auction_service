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
//    private final ReservationService reservationService;
//    private final WorkHourService workHourService;
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

//    public boolean compareSecurityCustomerEmailAndEmailByReservationId(Long reservationId) {
//        String reservationCustomerEmail = reservationService.getCustomerEmailByReservationId(reservationId);
//        return getUserEmail().equals(reservationCustomerEmail);
//    }

//    public boolean compareSecurityCustomerOrProviderEmailAndEmailByReservationId(Long reservationId) {
//        String reservationCustomerEmail = reservationService.getCustomerEmailByReservationId(reservationId);
//        String reservationProviderEmail = reservationService.getProviderEmailByReservationId(reservationId);
//        return getUserEmail().equals(reservationCustomerEmail) || getUserEmail().equals(reservationProviderEmail);
//    }

//    public boolean compareSecurityProviderEmailAndEmailByReservationId(Long reservationId) {
//        String reservationCustomerEmail = reservationService.getProviderEmailByReservationId(reservationId);
//        return getUserEmail().equals(reservationCustomerEmail);
//    }

    public boolean compareSecurityEmailAndEmailByOfferingId(Long auctionId) {
        String reservationCustomerEmail = auctionService.getProviderEmailByAuctionId(auctionId);
        return getUserEmail().equals(reservationCustomerEmail);
    }

//    public boolean compareSecurityEmailAndEmailByWorkHourId(Long workHourId) {
//        String addressOwnerEmail = workHourService.getProviderEmailByWorkHourId(workHourId);
//        return getUserEmail().equals(addressOwnerEmail);
//    }

}
