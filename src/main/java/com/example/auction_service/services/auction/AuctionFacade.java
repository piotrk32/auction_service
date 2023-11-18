package com.example.auction_service.services.auction;

import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.dtos.*;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.services.provider.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.auction_service.models.auction.dtos.AuctionMapper.mapToAuctionResponseDTO;

@Component
@RequiredArgsConstructor
public class AuctionFacade {

    private final AuctionService auctionService;
    private final ProviderService providerService;

    public AuctionResponseDTO createAuction(AuctionInputDTO auctionInputDTO, List<Long> itemIds) {
        Provider provider = providerService.getProviderById(auctionInputDTO.providerId());
        Auction auction = auctionService.createAuction(auctionInputDTO, provider, itemIds);
        return mapToAuctionResponseDTO(auction);
    }

    public AuctionResponseDTO getAuctionById(Long auctionId) {
        Auction auction = auctionService.getAuctionById(auctionId);
        return mapToAuctionResponseDTO(auction);
    }

    public void deleteAuctionById(Long auctionId) {
        auctionService.deleteAuctionById(auctionId);
    }

    public Page<AuctionResponseDTO> getAuctions(AuctionRequestDTO auctionRequestDTO) {
        return auctionService.getAuctions(auctionRequestDTO).map(AuctionMapper::mapToAuctionResponseDTO);
    }
    public Page<AuctionResponseDTO> getAuctionsByProviderId(Long providerId, AuctionProviderRequestDTO auctionProviderRequestDTO) {
        return auctionService.getAuctionsByProviderId(providerId, auctionProviderRequestDTO).map(AuctionMapper::mapToAuctionResponseDTO);
    }

    public AuctionResponseDTO updateAuctionById(Long auctionId, AuctionInputDTO auctionInputDTO, List<Long> newItemIds) {
        Auction updatedAuction = auctionService.updateAuctionById(auctionId, auctionInputDTO, newItemIds);
        return mapToAuctionResponseDTO(updatedAuction);
    }

}
