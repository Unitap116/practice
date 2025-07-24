package com.unitap.api;

import com.unitap.dto.response.CardResponse;
import com.unitap.utils.BaseExceptionMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/card")
@Tag(name = "Card", description = "Describing endpoints that familiar to cards")
public interface CardApi {

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user's card by his Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card successfully received",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CardResponse.class))),
            @ApiResponse(responseCode = "404", description = "Card not found by user's Id",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Card access denied exception received - insufficient permissions",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    CardResponse getCard(@Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
                         @PathVariable String userId);

    @GetMapping(value = "/{userId}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve QR code that contains link to the user's card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code successfully received",
                content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE))
    })
    byte[] getCardQr(@Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
                     @PathVariable String userId);
}
