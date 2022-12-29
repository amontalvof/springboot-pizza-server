package com.pizza.server.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pizza.server.errors.ErrorResponse;
import com.pizza.server.models.Product;
import com.pizza.server.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Product", description = "Product API")
@CrossOrigin(origins = "${CLIENT_URL}", allowedHeaders = "*")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of a product", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: failed creation", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(method = RequestMethod.POST, value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Read products")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of products", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    @RequestMapping(method = RequestMethod.GET, value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> readProducts() {
        List<Product> products = productService.readProducts();
        System.out.println("Products: " + products);
        return products;
    }

    @Operation(summary = "Read a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Product doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of a product", content = @Content(schema = @Schema(implementation = Product.class))),
    })
    @RequestMapping(method = RequestMethod.GET, value = "/api/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> readProduct(@PathVariable Long id) {
        Product storedProduct = productService.readProduct(id);
        return new ResponseEntity<>(storedProduct, HttpStatus.OK);
    }

    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Product doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of a product", content = @Content(schema = @Schema(implementation = Product.class))),
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/api/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product, @PathVariable Long id) {
        Product storedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(storedProduct, HttpStatus.OK);
    }

    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Product doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful removal of a product", content = @Content(schema = @Schema(implementation = Product.class))),
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Product storedProduct = productService.deleteProduct(id);
        return new ResponseEntity<>(storedProduct, HttpStatus.OK);
    }

}
