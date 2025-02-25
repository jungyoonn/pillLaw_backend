package com.eeerrorcode.pilllaw.service.product;

import com.eeerrorcode.pilllaw.dto.product.ProductPriceDto;
import com.eeerrorcode.pilllaw.entity.product.ProductPrice;
import com.eeerrorcode.pilllaw.repository.product.ProductPriceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    public ProductPriceServiceImpl(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    @Override
    public Optional<ProductPriceDto> getProductPrice(Long pno) {
        return productPriceRepository.findByProductPno(pno)
            .map(this::toDto); 
    }
    
}
