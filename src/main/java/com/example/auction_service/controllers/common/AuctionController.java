package com.example.auction_service.controllers.common;



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
@RequestMapping("/common/auction")
@Tag(name = "Common auction Controller",
        description = "Controller for auction management, designed for customer and provider")
public class AuctionController {

    private final AuctionFacade auctionFacade;

    @Operation(summary = "Get auction by id", description = "Retrieves an auction using the given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful getting auction",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuctionResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - resource could not be found on the server",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionResponseDTO> getAuctionById(@PathVariable Long auctionId) {
        AuctionResponseDTO auctionResponseDTO = auctionFacade.getAuctionById(auctionId);
        return ResponseEntity.ok(auctionResponseDTO);
    }



}

