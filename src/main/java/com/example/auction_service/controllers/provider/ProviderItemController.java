package com.example.auction_service.controllers.provider;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.auction.dtos.AuctionResponseDTO;
import com.example.auction_service.models.item.dtos.ItemInputDTO;
import com.example.auction_service.models.item.dtos.ItemResponseDTO;
import com.example.auction_service.models.item.dtos.ItemUpdateDTO;
import com.example.auction_service.services.auction.AuctionFacade;
import com.example.auction_service.services.item.ItemFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider/items")
@RequiredArgsConstructor
@Tag(name = "Provider Item Controller", description = "Controller for item management by providers")
public class ProviderItemController {

    private final AuctionFacade auctionFacade;
    private final ItemFacade itemFacade;
    @Operation(summary = "Assign items to auction", description = "Assigns a list of items to a specific auction")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful assignment of items to auction",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuctionResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Auction or one or more items not found",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @PostMapping("/auction/{auctionId}/assign-items")
    public ResponseEntity<AuctionResponseDTO> assignItemsToAuction(
            @PathVariable Long auctionId,
            @RequestBody List<Long> itemIds) {

        AuctionResponseDTO auctionResponseDTO = auctionFacade.assignItemsToAuction(auctionId, itemIds);
        return new ResponseEntity<>(auctionResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Create new item", description = "Creates a new item from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful creation of item",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemResponseDTO.class)
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
//    @PreAuthorize("(@fineGrainServices.getCurrentUserId()==#itemInputDTO.providerId())")
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody ItemInputDTO itemInputDTO) {
            ItemResponseDTO itemResponseDTO = itemFacade.createItem(itemInputDTO);
            return new ResponseEntity<>(itemResponseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove an item from an auction", description = "Remove a specific item from a given auction")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item successfully removed from the auction",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "404",
                    description = "Auction or Item not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{auctionId}/items/{itemId}")
    public ResponseEntity<?> removeItemFromAuction(@PathVariable Long auctionId, @PathVariable Long itemId) {
        try {
            itemFacade.removeItemFromAuction(auctionId, itemId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Entity Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error", "An unexpected error occurred"));
        }
    }

    @Operation(summary = "Update existing item", description = "Updates an item based on the provided ID and payload")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful update of item",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - returns map of errors",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Item not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable Long itemId, @Validated @RequestBody ItemUpdateDTO itemUpdateDTO) {
        try {
            ItemResponseDTO updatedItem = itemFacade.updateItemById(itemId, itemUpdateDTO);
            return ResponseEntity.ok(updatedItem);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Item Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error", e.getMessage()));
        }
    }

    @Operation(summary = "Delete existing item", description = "Deletes an item based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successful deletion of item"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Item not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItemById(@PathVariable Long itemId) {
        try {
            itemFacade.deleteItemById(itemId);
            return ResponseEntity.noContent().build(); // 204 No Content status
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Item Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error", e.getMessage()));
        }
    }





}