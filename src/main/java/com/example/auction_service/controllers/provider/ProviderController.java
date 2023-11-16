package com.example.auction_service.controllers.provider;

import com.example.auction_service.exceptions.ErrorMessage;
import com.example.auction_service.models.customer.dtos.CustomerRequestDTO;
import com.example.auction_service.models.customer.dtos.CustomerResponseDTO;
import com.example.auction_service.models.provider.dtos.ProviderInputDTO;
import com.example.auction_service.models.provider.dtos.ProviderResponseDTO;
import com.example.auction_service.services.provider.ProviderFacade;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/provider")
@Tag(name = "Provider Controller", description = "Functionalities intended for the providers")
public class ProviderController {

    private final ProviderFacade providerFacade;

    @Operation(summary = "Delete provider by id", description = "Deletes provider by his id - changes flag to inactive")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successful delete provider",
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
    @PatchMapping("/delete/{id}")
    @PreAuthorize("@fineGrainServices.compareSecurityEmailAndEmailByUserId(#id)")
    public ResponseEntity<Void> deleteProviderById(@PathVariable Long id) {
        providerFacade.deleteProviderById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update provider by id", description = "Updates provider by his id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful update provider",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProviderResponseDTO.class)
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - temporally returns map of errors or ErrorMessage",
                    content = @Content(
                            mediaType = "application/json"
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - Provider not found",
                    content = @Content(
                            mediaType = "application/json"
                    ))
    })
    @PutMapping("/update/{id}")
//    @PreAuthorize("@fineGrainServices.compareSecurityEmailAndEmailByUserId(#id)")
    public ResponseEntity<ProviderResponseDTO> updateProviderById(
            @PathVariable Long id, @RequestBody @Valid ProviderInputDTO providerInputDTO) {
        ProviderResponseDTO updatedProvider = providerFacade.updateProviderById(id, providerInputDTO);
        return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }

    @Operation(summary = "Get customers by provider id",
            description = "Retrieves all customers of provider by provider Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customers obtained successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = CustomerResponseDTO.class
                                    ))
                    ))
    })
    @GetMapping("/customers/{providerId}")
//    @PreAuthorize("@fineGrainServices.compareSecurityEmailAndEmailByUserId(#providerId)")
    public ResponseEntity<Page<CustomerResponseDTO>> getCustomersByProviderId(@PathVariable Long providerId, @ModelAttribute @Valid CustomerRequestDTO customerRequestDTO) {
        return new ResponseEntity<>(providerFacade.getCustomersByProviderId(providerId, customerRequestDTO), HttpStatus.OK);
    }

}
