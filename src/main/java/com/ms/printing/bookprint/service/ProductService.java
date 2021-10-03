package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.models.dto.ProductInfo;
import com.ms.printing.bookprint.models.dto.SummaryResponse;

public interface ProductService {

    SummaryResponse<ProductInfo> browse(int page, int size);

}
