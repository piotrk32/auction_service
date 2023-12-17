package com.example.auction_service.controllers.provider;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.dtos.AuctionInputDTO;
import com.example.auction_service.models.auction.dtos.AuctionProviderRequestDTO;
import com.example.auction_service.models.auction.dtos.AuctionResponseDTO;
import com.example.auction_service.models.bid.dtos.BidRequestDTO;
import com.example.auction_service.models.bid.dtos.BidResponseDTO;
import com.example.auction_service.security.services.FineGrainServices;
import com.example.auction_service.services.auction.AuctionFacade;
import com.example.auction_service.services.bid.BidFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/provider/auction")
@Tag(name = "Provider Auction Controller")
public class ProviderAuctionController {

    private final AuctionFacade auctionFacade;
    private final FineGrainServices fineGrainServices;
    private final BidFacade bidFacade;
    @Operation(summary = "Create new auction", description = "Creates a auction from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful creation of auction",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuctionResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - returns map of errors",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Provider not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PostMapping("")
    public ResponseEntity<AuctionResponseDTO> createAuction(
            @RequestBody  AuctionInputDTO auctionInputDTO,
            @RequestParam(required = false) List<Long> itemIds) {
        Long providerId = fineGrainServices.getCurrentUserId();
        AuctionResponseDTO auctionResponseDTO = auctionFacade.createAuction(auctionInputDTO, itemIds, providerId);
        return new ResponseEntity<>(auctionResponseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Update auction by id", description = "Updates the auction with the given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful update of auction",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuctionResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - returns map of errors",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Auction or Provider not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PutMapping("/update/{auctionId}")
    public ResponseEntity<AuctionResponseDTO> updateAuction(
            @PathVariable Long auctionId,
            @RequestBody AuctionInputDTO auctionInputDTO,
            @RequestParam(required = false) List<Long> newItemIds) {

        AuctionResponseDTO auctionResponseDTO = auctionFacade.updateAuctionById(auctionId, auctionInputDTO, newItemIds);
        return ResponseEntity.ok(auctionResponseDTO);
    }

    @Operation(summary = "Delete auction by id", description = "Deletes the auction with the given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successful deletion of auction",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid auction ID",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Auction not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @DeleteMapping("/delete/{auctionId}")
    public ResponseEntity<?> deleteAuctionById(@PathVariable Long auctionId) {
        try {
            auctionFacade.deleteAuctionById(auctionId);
            return ResponseEntity.noContent().build(); // 204 No Content status
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Auction Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error", e.getMessage()));
        }
    }

    @Operation(summary = "Get auctions by provider", description = "Retrieves auctions for a given provider")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of auctions",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Provider not found",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @GetMapping("/all/{providerId}")
    public ResponseEntity<Page<AuctionResponseDTO>> getAuctionsByProvider(@PathVariable Long providerId,
                                                                          @ModelAttribute @Valid AuctionProviderRequestDTO auctionProviderRequestDTO) {
        Page<AuctionResponseDTO> auctionResponseDTOPage = auctionFacade.getAuctionsByProviderId(providerId, auctionProviderRequestDTO);
        return ResponseEntity.ok(auctionResponseDTOPage);
    }

    @Operation(summary = "Activate auction", description = "Activates the auction with the given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful activation of auction",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Auction.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Auction not found",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @PatchMapping("/activate/{auctionId}")
    public ResponseEntity<AuctionResponseDTO> activateAuction(@PathVariable Long auctionId) {
        AuctionResponseDTO auctionResponseDTO = auctionFacade.activateAuction(auctionId);
        return ResponseEntity.ok(auctionResponseDTO);
    }

    @Operation(summary = "Deactivate auction", description = "Deactivates the auction with the given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful deactivation of auction",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Auction.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Auction not found",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @PatchMapping("/{auctionId}/deactivate")
    public ResponseEntity<AuctionResponseDTO> deactivateAuction(@PathVariable Long auctionId) {
        AuctionResponseDTO auctionResponseDTO = auctionFacade.deactivateAuction(auctionId);
        return ResponseEntity.ok(auctionResponseDTO);
    }

    @Operation(summary = "Get bids for auction", description = "Retrieves bids for a given auction with optional sorting and filtering")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of bids",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Auction not found",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid parameters",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @GetMapping("/by-auction/{auctionId}")
    public ResponseEntity<Page<BidResponseDTO>> getBidsByAuction(@PathVariable Long auctionId,
                                                                 @ModelAttribute BidRequestDTO bidRequestDTO) {
        Page<BidResponseDTO> bidResponseDTOPage = bidFacade.getBidsByAuctionId(auctionId, bidRequestDTO);
        return ResponseEntity.ok(bidResponseDTOPage);
    }
}






