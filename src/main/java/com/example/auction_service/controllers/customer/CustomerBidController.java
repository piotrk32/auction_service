package com.example.auction_service.controllers.customer;

import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.bid.dtos.BidInputDTO;
import com.example.auction_service.models.bid.dtos.BidResponseDTO;
import com.example.auction_service.services.bid.BidFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "Customer Bid Controller", description = "Functionalities intended for the customers to manage bids")
public class CustomerBidController {

    private final BidFacade bidFacade;

    @Operation(summary = "Create new bid for auction", description = "Creates a bid for the specified auction from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful creation of bid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BidResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - returns map of errors",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Auction or Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PostMapping("")
    public ResponseEntity<BidResponseDTO> createBidForAuction(
            @RequestBody BidInputDTO bidInputDTO) {

        // Utworzenie oferty
        BidResponseDTO bidResponseDTO = bidFacade.createBid(bidInputDTO);
        return new ResponseEntity<>(bidResponseDTO, HttpStatus.CREATED);
    }


}
