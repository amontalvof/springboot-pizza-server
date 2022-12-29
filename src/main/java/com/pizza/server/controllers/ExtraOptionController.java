package com.pizza.server.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pizza.server.errors.ErrorResponse;
import com.pizza.server.models.ExtraOption;
import com.pizza.server.services.ExtraOptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;

@Tag(name = "ExtraOption", description = "ExtraOption API")
@RestController
public class ExtraOptionController {
    @Autowired
    private ExtraOptionService ExtraOptionService;

    @Operation(summary = "Create an extra option", description = "Create an extra option by product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of a Extra Option", content = @Content(schema = @Schema(implementation = ExtraOption.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: failed creation", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(method = RequestMethod.POST, value = "/api/products/{productId}/options", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtraOption> createExtraOption(@RequestBody @Valid ExtraOption extraOption,
            @PathVariable Long productId) {
        ExtraOption createdExtraOption = ExtraOptionService.createExtraOption(productId, extraOption);
        return new ResponseEntity<>(createdExtraOption, HttpStatus.CREATED);
    }

    @Operation(summary = "Read extra options", description = "Read extra options by product id")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of Extra Options", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExtraOption.class))))
    @RequestMapping(method = RequestMethod.GET, value = "/api/products/{productId}/options", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExtraOption> findByProductId(@PathVariable Long productId) {
        return ExtraOptionService.findByProductId(productId);
    }
}
