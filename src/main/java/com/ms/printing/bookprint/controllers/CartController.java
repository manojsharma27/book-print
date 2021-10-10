package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Product;
import com.ms.printing.bookprint.models.dto.CartDto;
import com.ms.printing.bookprint.models.dto.CartOperationResponse;
import com.ms.printing.bookprint.service.CartService;
import com.ms.printing.bookprint.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(value = "/v1/cart", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "Cart APIs", description = "The cart related operations API")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private JsonUtil jsonUtil;

    @RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get the cart details for the provided cartId")
    public ResponseEntity<CartDto> getCart(@NotEmpty @ApiParam(name = "cartId", required = true) @PathVariable(value = "cartId") String cartId) {
        CartDto cart = cartService.getCart(UUID.fromString(cartId));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get the cart details of the customer identified by customerId")
    public ResponseEntity<CartDto> getCartForUser(@NotEmpty @ApiParam(name = "customerId", required = true) @RequestParam(value = "customerId") String customerId) {
        CartDto cart = cartService.getCartForUser(UUID.fromString(customerId));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/addProduct", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = "POST", value = "Adds the product to the specified cart")
    public ResponseEntity<OperationResponse> addProduct(@NotEmpty @ApiParam(name = "cartId", required = true) @PathVariable(value = "cartId") String cartId,
                                                        @Validated @RequestBody Product product) {
        UUID cartIdUuid = UUID.fromString(cartId);
        UUID productId = cartService.addProduct(cartIdUuid, product);
        OperationResponse response = OperationResponse.builder()
                .message("Product added to the cart!")
                .cartId(cartIdUuid)
                .productId(productId)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/removeProduct", method = RequestMethod.POST)
    public ResponseEntity<OperationResponse> removeProduct(@NotEmpty @ApiParam(name = "cartId", required = true) @PathVariable(value = "cartId") String cartId,
                                                           @NotEmpty @ApiParam(name = "productId", required = true) @RequestParam(value = "productId") String productId) {
        UUID cartIdUuid = UUID.fromString(cartId);
        UUID productIdUuid = UUID.fromString(productId);
        cartService.removeProduct(cartIdUuid, productIdUuid);
        OperationResponse response = OperationResponse.builder()
                .message("Product removed from the cart!")
                .cartId(cartIdUuid)
                .productId(productIdUuid)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/updateQuantity", method = RequestMethod.PUT)
    public ResponseEntity<OperationResponse> updateQuantity(@NotEmpty @ApiParam(name = "cartId", required = true) @PathVariable(value = "cartId") String cartId,
                                                            @NotEmpty @ApiParam(name = "productId", required = true) @RequestParam(value = "productId") String productId,
                                                            @ApiParam(name = "quantity", required = true) @RequestParam(value = "quantity") int quantity) {
        UUID cartIdUuid = UUID.fromString(cartId);
        UUID productIdUuid = UUID.fromString(productId);
        int updatedQuantity = cartService.updateQuantity(cartIdUuid, productIdUuid, quantity);
        OperationResponse response = OperationResponse.builder()
                .message("Updated product quantity for the cart!")
                .cartId(cartIdUuid)
                .productId(productIdUuid)
                .quantity(updatedQuantity)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}/clear", method = RequestMethod.POST)
    public ResponseEntity<CartOperationResponse> clearCart(@ApiParam(name = "cartId", required = true) @RequestParam(value = "cartId") String cartId) {
        UUID cartIdUuid = UUID.fromString(cartId);
        cartService.clear(cartIdUuid);
        OperationResponse response = OperationResponse.builder()
                .message("Cart is cleared!")
                .cartId(cartIdUuid)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
    public ResponseEntity<OperationResponse> delete(@NotEmpty @ApiParam(name = "cartId", required = true) @PathVariable(value = "cartId") String cartId) {
        UUID cartIdUuid = UUID.fromString(cartId);
        cartService.deleteCart(cartIdUuid);
        OperationResponse response = OperationResponse.builder()
                .message("Cart and related products are deleted!")
                .cartId(cartIdUuid)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}