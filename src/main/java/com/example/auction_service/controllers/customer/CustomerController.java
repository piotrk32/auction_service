package com.example.auction_service.controllers.customer;

import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.customer.dtos.CustomerInputDTO;
import com.example.auction_service.models.customer.dtos.CustomerResponseDTO;
import com.example.auction_service.services.customer.CustomerFacade;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "Customer Controller", description = "Functionalities intended for the customers")
public class CustomerController {

    private final CustomerFacade customerFacade;

    @Operation(summary = "Delete customer by id", description = "Changes customer's status from ACTIVE to INACTIVE")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No content - status changed"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PatchMapping("/delete/{id}")
    @PreAuthorize("@fineGrainServices.compareSecurityEmailAndEmailByUserId(#id)")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerFacade.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update customer by id", description = "Updates customer's personal data")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - temporally returns map of errors or ErrorMessage.",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("@fineGrainServices.compareSecurityEmailAndEmailByUserId(#id)")
    public ResponseEntity<CustomerResponseDTO> updateCustomerById(
            @PathVariable Long id, @RequestBody @Valid CustomerInputDTO customerInputDTO) {
        CustomerResponseDTO updatedCustomer = customerFacade.updateCustomerById(id, customerInputDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

}
