package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.models.dto.CustomerOperationResponse;
import com.ms.printing.bookprint.service.CustomerService;
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
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(value = "/v1/customer", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Create a customer")
    public ResponseEntity<CustomerOperationResponse> create(@Validated @ApiParam(name = "customer", required = true) @RequestBody Customer customer) {
        UUID customerId = customerService.create(customer);
        return new ResponseEntity<>(CustomerOperationResponse.builder().customerId(customerId).message("Created Customer!").build(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get customer details by customerId")
    public ResponseEntity<Customer> read(@NotEmpty @ApiParam(name = "customerId", required = true) @PathVariable("customerId") String customerId) {
        Customer customer = customerService.getById(UUID.fromString(customerId));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get customer details by email")
    public ResponseEntity<Customer> readByEmail(@NotEmpty @ApiParam(name = "email", required = true) @RequestParam("email") String email) {
        Customer customer = customerService.getByEmail(email);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
    @ApiOperation(httpMethod = "DELETE", value = "Delete a customer customerId")
    public ResponseEntity<CustomerOperationResponse> delete(@NotEmpty @ApiParam(name = "customerId", required = true) @PathVariable("customerId") String customerId) {
        UUID customerIdUuid = UUID.fromString(customerId);
        customerService.delete(customerIdUuid);
        return new ResponseEntity<>(CustomerOperationResponse.builder().customerId(customerIdUuid).message("Deleted customer").build(), HttpStatus.OK);
    }
}