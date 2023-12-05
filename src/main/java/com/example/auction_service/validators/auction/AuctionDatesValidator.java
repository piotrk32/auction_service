package com.example.auction_service.validators.auction;


import com.example.auction_service.models.auction.dtos.AuctionInputDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuctionDatesValidator implements ConstraintValidator<ValidAuctionDates, AuctionInputDTO> {

    @Override
    public void initialize(ValidAuctionDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(AuctionInputDTO auctionInputDTO, ConstraintValidatorContext context) {
        if (auctionInputDTO.auctionDate() == null || auctionInputDTO.auctionDateEnd() == null) {
            return true; // Można zwrócić false, jeśli daty są wymagane
        }
        return auctionInputDTO.auctionDate().isBefore(auctionInputDTO.auctionDateEnd());
    }
}
