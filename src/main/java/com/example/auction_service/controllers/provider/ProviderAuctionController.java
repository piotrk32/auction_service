package com.example.auction_service.controllers.provider;

import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.auction.dtos.AuctionInputDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/provider/auction")
@Tag(name = "Provider Controller")
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
//    @PostMapping("")
////    @PreAuthorize("(@fineGrainServices.getCurrentUserId()==#auctionInputDTO.providerId())")
//    public ResponseEntity<AuctionResponseDTO> createAuction(@RequestBody @Valid AuctionInputDTO auctionInputDTO) {
//        AuctionResponseDTO auctionResponseDTO = auctionFacade.createAuction(auctionInputDTO);
//        return new ResponseEntity<>(auctionResponseDTO, HttpStatus.CREATED);
//    }



}
