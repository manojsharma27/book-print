package com.ms.printing.bookprint.controllers;

import com.ms.printing.bookprint.models.Customer;
import com.ms.printing.bookprint.models.dto.ProductInfo;
import com.ms.printing.bookprint.models.dto.SummaryResponse;
import com.ms.printing.bookprint.service.CustomerService;
import com.ms.printing.bookprint.service.ProductService;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.awt.print.Pageable;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductController {

    @Resource
    private ProductService productService;

    @RequestMapping(value = "/browse", method = RequestMethod.GET)
    public ResponseEntity<SummaryResponse<ProductInfo>> browse(@ApiParam(name = "page", defaultValue = "0") @RequestParam("page") int page,
                                                    @ApiParam(name = "size", defaultValue = "10") @RequestParam("size") int size) {
        SummaryResponse<ProductInfo> summaryResponse = productService.browse(page, size);
        return new ResponseEntity<>(summaryResponse, HttpStatus.OK);
    }

}