package com.arthurprojects.pezesha.controller;

import com.arthurprojects.pezesha.dto.TransferDTO;
import com.arthurprojects.pezesha.dto.TransferResponseDTO;
import com.arthurprojects.pezesha.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfer Management", description = "APIs for managing money transfers between accounts")
public class TransferController {
    private final TransferService transferService;

    @Operation(
            summary = "Create new transfer",
            description = "Initiates a money transfer between two accounts"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Transfer completed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TransferResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid transfer request or insufficient funds",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Source or destination account not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)
                    )
            )
    })

    @PostMapping
    public ResponseEntity<TransferResponseDTO> createTransfer(@RequestBody @Valid TransferDTO transferDTO) {
        return ResponseEntity.ok(transferService.createTransfer(transferDTO));
    }
}
