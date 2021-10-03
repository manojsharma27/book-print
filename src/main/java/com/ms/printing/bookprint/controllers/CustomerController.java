package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.service.CustomerService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test value";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@ApiParam(name = "customer", required = true) @RequestBody Customer customer) {
        UUID uuid = customerService.create(customer);
        return new ResponseEntity<>(uuid.toString(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Customer> read(@ApiParam(name = "customerId", required = true) @PathVariable("customerId") String customerId) {
        Customer customer = customerService.getById(UUID.fromString(customerId));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Customer> readByEmail(@ApiParam(name = "email", required = true) @RequestParam("email") String email) {
        Customer customer = customerService.getByEmail(email);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@ApiParam(name = "customerId", required = true) @PathVariable("customerId") String customerId) {
        customerService.delete(UUID.fromString(customerId));
        return new ResponseEntity<>("Deleted customer", HttpStatus.OK);
    }
}