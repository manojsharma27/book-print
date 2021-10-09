package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Order;
import com.ms.printing.bookprint.models.ShipmentTrackingDetails;
import com.ms.printing.bookprint.models.dto.OperationResponse;
import com.ms.printing.bookprint.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "book-print", description = "The order related operations API")
public class OrderController {

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get the order details for the provided orderId")
    public ResponseEntity<Order> getOrder(@ApiParam(name = "orderId", required = true) @PathVariable(value = "orderId") String orderId) {
        Order order = orderService.getOrder(UUID.fromString(orderId));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get the order details of the customer identified by customerId")
    public ResponseEntity<List<Order>> getOrdersForUser(@ApiParam(name = "customerId", required = true) @RequestParam(value = "customerId") String customerId) {
        List<Order> orders = orderService.getOrdersForUser(UUID.fromString(customerId));
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<OperationResponse> delete(@ApiParam(name = "orderId", required = true) @PathVariable("orderId") String orderId) {
        UUID orderIdUuid = UUID.fromString(orderId);
        orderService.delete(orderIdUuid);
        return new ResponseEntity<>(OperationResponse.builder().orderId(orderIdUuid).message("Deleted order").build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{orderId}/track", method = RequestMethod.GET)
    public ResponseEntity<ShipmentTrackingDetails> trackShipment(@ApiParam(name = "orderId", required = true) @PathVariable("orderId") String orderId) {
        UUID orderIdUuid = UUID.fromString(orderId);
        ShipmentTrackingDetails shipmentTrackingDetails = orderService.trackShipment(orderIdUuid);
        return new ResponseEntity<>(shipmentTrackingDetails, HttpStatus.OK);
    }

}
