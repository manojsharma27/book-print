package com.ms.printing.bookprint.service;

import com.ms.printing.bookprint.models.dto.ProductInfo;
import com.ms.printing.bookprint.models.dto.Summary;
import com.ms.printing.bookprint.models.dto.SummaryResponse;
import com.ms.printing.bookprint.repositories.ProductInfoRepository;
import com.ms.printing.bookprint.repositories.entities.ProductInfoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductInfoRepository productInfoRepository;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public SummaryResponse<ProductInfo> browse(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<ProductInfoEntity> pageEntityResult = productInfoRepository.findAll(pageRequest);
        Page<ProductInfo> resultPage = pageEntityResult.map(productInfoEntity -> {
            ProductInfo productInfo = modelMapper.map(productInfoEntity, ProductInfo.class);
            productInfo.setProductType(productInfoEntity.getProductTypeEntity().getType());
            return productInfo;
        });
        List<ProductInfo> productInfos = resultPage.get().collect(Collectors.toList());
        SummaryResponse<ProductInfo> summaryResponse = new SummaryResponse<>(Summary.builder().page(page).size(size).build(), productInfos);
        return summaryResponse;
    }
}
