package com.example.auction_service.controllers.provider;

import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.auction.Auction;
import com.example.auction_service.models.auction.dtos.AuctionInputDTO;
import com.example.auction_service.models.auction.dtos.AuctionProviderRequestDTO;
import com.example.auction_service.models.auction.dtos.AuctionResponseDTO;
import com.example.auction_service.services.auction.AuctionFacade;
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
    //    @PreAuthorize("(@fineGrainServices.getCurrentUserId()==#auctionInputDTO.providerId())")
    public ResponseEntity<AuctionResponseDTO> createAuction(
            @RequestBody @Valid AuctionInputDTO auctionInputDTO,
            @RequestParam(required = false) List<Long> itemIds) {

        AuctionResponseDTO auctionResponseDTO = auctionFacade.createAuction(auctionInputDTO, itemIds);
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
    @PutMapping("/{auctionId}")
    public ResponseEntity<AuctionResponseDTO> updateAuction(
            @PathVariable Long auctionId,
            @RequestBody @Valid AuctionInputDTO auctionInputDTO,
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
    @DeleteMapping("/{auctionId}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long auctionId) {
        auctionFacade.deleteAuctionById(auctionId);
        return ResponseEntity.noContent().build();
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
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<Page<AuctionResponseDTO>> getAuctionsByProvider(@PathVariable Long providerId, AuctionProviderRequestDTO auctionProviderRequestDTO) {
        Page<AuctionResponseDTO> auctions = auctionFacade.getAuctionsByProviderId(providerId, auctionProviderRequestDTO);
        return new ResponseEntity<>(auctions, HttpStatus.OK);
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
    @PatchMapping("/{auctionId}/activate")
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





}
