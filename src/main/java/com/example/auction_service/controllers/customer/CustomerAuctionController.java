package com.example.auction_service.controllers.customer;

import com.example.auction_service.models.auction.dtos.AuctionRequestDTO;
import com.example.auction_service.models.auction.dtos.AuctionResponseDTO;
import com.example.auction_service.services.auction.AuctionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/auction")
@Tag(name = "Customer auction Controller",
        description = "Controller for auction management, designed for customer ")
public class CustomerAuctionController {

    private final AuctionFacade auctionFacade;

    @GetMapping("/all")
    @Operation(summary = "Show all auctions", description = "Functionality lets user to show all available auctions")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful auction acquisition",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AuctionResponseDTO.class)
                            )
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - temporally returns map of errors or ErrorMessage",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    public ResponseEntity<Page<AuctionResponseDTO>> getAuctions(
            @ModelAttribute @Valid AuctionRequestDTO auctionRequestDTO) {
        Page<AuctionResponseDTO> auctionResponseDTOPage = auctionFacade.getAuctions(auctionRequestDTO);
        return new ResponseEntity<>(auctionResponseDTOPage, HttpStatus.OK);
    }



}