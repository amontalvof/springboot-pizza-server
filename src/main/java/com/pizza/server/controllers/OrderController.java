package com.pizza.server.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;

import com.pizza.server.errors.ErrorResponse;
import com.pizza.server.models.Order;
import com.pizza.server.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Order", description = "Order API")
@CrossOrigin(origins = "${CLIENT_URL}", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of an order", content = @Content(schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: failed creation", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(method = RequestMethod.POST, value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody @Valid Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @Operation(summary = "Read orders")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of orders", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    @RequestMapping(method = RequestMethod.GET, value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> readOrders() {
        List<Order> orders = orderService.readOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(summary = "Read an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Order doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of a order", content = @Content(schema = @Schema(implementation = Order.class))),
    })
    @RequestMapping(method = RequestMethod.GET, value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> readOrder(@PathVariable Long id) {
        Order storedOrder = orderService.readOrder(id);
        return new ResponseEntity<>(storedOrder, HttpStatus.OK);
    }

    @Operation(summary = "Update an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Order doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of an order", content = @Content(schema = @Schema(implementation = Order.class))),
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> updateOrder(@RequestBody @Valid Order order, @PathVariable Long id) {
        Order storedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(storedOrder, HttpStatus.OK);
    }

}
