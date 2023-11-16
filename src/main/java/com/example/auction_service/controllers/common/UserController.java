package com.example.auction_service.controllers.common;

import com.example.auction_service.models.customer.dtos.CustomerResponseDTO;
import com.example.auction_service.models.provider.dtos.ProviderResponseDTO;
import com.example.auction_service.services.customer.CustomerFacade;
import com.example.auction_service.services.provider.ProviderFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common/user")
@Tag(name = "Common User Controller",
        description = "Functionalities intended for the customers and provider")
public class UserController {

    private final CustomerFacade customerFacade;
    private final ProviderFacade providerFacade;

    @Operation(summary = "Get customer by id", description = "Retrieves a customer by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer obtained successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - resource could not be found on the server",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerFacade.getCustomerById(customerId), HttpStatus.OK);
    }

    @Operation(summary = "Get provider by id", description = "Retrieves a provider by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Provider obtained successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProviderResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - resource could not be found on the server",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ProviderResponseDTO> getProviderById(@PathVariable Long providerId) {
        return new ResponseEntity<>(providerFacade.getProviderById(providerId), HttpStatus.OK);
    }

}