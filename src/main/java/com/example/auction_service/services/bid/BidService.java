package com.example.auction_service.services.bid;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.enums.StatusAuction;
import com.example.auction_service.models.bid.Bid;
import com.example.auction_service.models.bid.dtos.BidInputDTO;
import com.example.auction_service.models.bid.dtos.BidMapper;
import com.example.auction_service.models.bid.dtos.BidResponseDTO;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.repositories.AuctionRepository;
import com.example.auction_service.repositories.BidRepository;
import com.example.auction_service.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final CustomerRepository customerRepository;


    public Bid getBidById(Long bidId) {
        return bidRepository.findById(bidId)
                .orElseThrow(() -> new EntityNotFoundException("Auction", "No auction found with id: " + bidId));
    }


    public BidResponseDTO createBid(BidInputDTO bidInputDTO) {
        // Pobierz aukcję i klienta na podstawie przekazanych identyfikatorów.
        Auction auction = auctionRepository.findById(bidInputDTO.auctionId())
                .orElseThrow(() -> new EntityNotFoundException("Auction", "Auction not found with id: " + bidInputDTO.auctionId()));

        Customer customer = customerRepository.findById(bidInputDTO.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer", "Customer not found with id: " + bidInputDTO.customerId()));

        // Sprawdź, czy aukcja jest aktywna i czy klient może wziąć udział.
        if (auction.getStatusAuction() == StatusAuction.ACTIVE) {
            // Tworzenie nowej oferty
            Bid bid = new Bid(customer, auction, bidInputDTO.bidValue());

            // Zapisz ofertę w bazie danych
            Bid savedBid = bidRepository.save(bid);

            // Ustaw nową ofertę jako zwycięską, jeśli jest najwyższą ofertą
            if (auction.getCurrentBid() == null || bidInputDTO.bidValue() > auction.getCurrentBid().getBidValue()) {
                auction.setCurrentBid(savedBid);
                auctionRepository.save(auction);

                // Zaktualizuj poprzednią zwycięską ofertę
                if (auction.getCurrentBid() != null) {
                    auction.getCurrentBid().setIsWinning(false);
                    bidRepository.save(auction.getCurrentBid());
                }

                savedBid.setIsWinning(true);
                bidRepository.save(savedBid);
            }
            updateWinningBidStatus(auction, savedBid);

            // Przygotuj odpowiedź DTO z informacjami o ofercie
            return BidMapper.mapToBidResponseDTO(savedBid);
        } else {
            // Aukcja nie jest aktywna, nie można składać ofert
            throw new IllegalStateException("Auction is not active, cannot place bids.");
        }
    }

    private void updateWinningBidStatus(Auction auction, Bid newBid) {
        // Pobierz aktualnie zwycięską ofertę
        Bid currentWinningBid = bidRepository.findTopByAuctionOrderByBidValueDesc(auction);

        if (currentWinningBid != null && !currentWinningBid.equals(newBid)) {
            // Jeśli istnieje inna oferta z wyższą wartością, zaktualizuj statusy
            if (newBid.getBidValue() > currentWinningBid.getBidValue()) {
                currentWinningBid.setIsWinning(false);
                bidRepository.save(currentWinningBid);

                newBid.setIsWinning(true);
                bidRepository.save(newBid);
            }
        } else {
            // Jeśli nie ma innych ofert, lub nowa oferta jest najwyższa
            newBid.setIsWinning(true);
            bidRepository.save(newBid);
        }
    }





}
