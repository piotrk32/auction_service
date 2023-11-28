package com.example.auction_service.controllers.customer;

import com.example.auction_service.models.item.dtos.ItemPurchaseDTO;
import com.example.auction_service.models.item.dtos.ItemResponseDTO;
import com.example.auction_service.services.customer.CustomerFacade;
import com.example.auction_service.services.item.ItemFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/item")
@RequiredArgsConstructor
@Tag(name = "Customer Item Controller", description = "Controller for managing item purchases by customers")
public class CustomerItemController {

    private final ItemFacade itemFacade;
    private final CustomerFacade customerFacade;



    @Operation(summary = "Buy item now", description = "Allows a customer to immediately purchase an item using the 'Buy Now' option")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful purchase of the item",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemPurchaseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - if the item cannot be purchased or parameters are invalid",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - if the item does not exist",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/buyNow/{itemId}")
    public ResponseEntity<ItemPurchaseDTO> buyItemNow(@PathVariable Long itemId, @RequestParam Long customerId) {
        ItemPurchaseDTO purchaseDTO = itemFacade.buyNow(itemId, customerId);
        return new ResponseEntity<>(purchaseDTO, HttpStatus.OK);
    }

//    @GetMapping("/items/{customerId}")
//    @Operation(summary = "Show all items acquired by customer", description = "Functionality lets user to show all items acquired by a specific customer")
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Successful retrieval of customer items",
//                    content = @Content(
//                            mediaType = "application/json",
//                            array = @ArraySchema(
//                                    schema = @Schema(implementation = ItemResponseDTO.class)
//                            )
//                    )),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Bad Request - returns map of errors or ErrorMessage",
//                    content = @Content(
//                            mediaType = "application/json"
//                    ))
//    })
//    public ResponseEntity<List<ItemResponseDTO>> getCustomerItems(@PathVariable Long customerId) {
//        List<ItemResponseDTO> itemResponseDTOList = customerFacade.findCustomerItems(customerId);
//        return new ResponseEntity<>(itemResponseDTOList, HttpStatus.OK);
//    }
}
