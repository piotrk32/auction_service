package com.example.auction_service.controllers.common;

import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.address.dtos.AddressResponseDTO;
import com.example.auction_service.models.address.dtos.AddressUpdateDTO;
import com.example.auction_service.services.address.AddressFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common/address")
@Tag(name = "Common Address Controller",
        description = "Controller for address management, designed for customer and provider")
public class AddressController {

    private final AddressFacade addressFacade;

    @Operation(summary = "Get address by id", description = "Retrieves an address using the given id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieve of address",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Address not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long addressId) {
        AddressResponseDTO addressResponseDTO = addressFacade.getAddressById(addressId);
        return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update address", description = "Updates an address from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful update of address",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - returns map of errors",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Address not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PutMapping("/update/{addressId}")
//    @PreAuthorize("(@fineGrainServices.compareSecurityEmailAndEmailByAddressId(#addressId))")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressUpdateDTO addressUpdateDTO) {
        AddressResponseDTO addressResponseDTO = addressFacade.updateAddress(addressId, addressUpdateDTO);
        return new ResponseEntity<>(addressResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete address", description = "Deletes an address with the given id - changes flag to inactive")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content - Successful deletion of address"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Address not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PatchMapping("/delete/{addressId}")
//    @PreAuthorize("(@fineGrainServices.compareSecurityEmailAndEmailByAddressId(#addressId))")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressFacade.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
