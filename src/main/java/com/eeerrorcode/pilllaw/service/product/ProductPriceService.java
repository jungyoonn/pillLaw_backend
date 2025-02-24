package com.eeerrorcode.pilllaw.service.product;

import com.eeerrorcode.pilllaw.dto.product.ProductPriceDto;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductPrice;

import java.util.Optional;

public interface ProductPriceService {
    Optional<ProductPriceDto> getProductPrice(Long pno);

  default ProductPrice toEntity(ProductPriceDto dto, Product product) {
      return ProductPrice.builder()
          .ppno(dto.getPpno())
          .product(product) 
          .price(dto.getPrice())
          .salePrice(dto.getSalePrice())
          .build();
  }

  default ProductPriceDto toDto(ProductPrice price) {
      return ProductPriceDto.builder()
          .ppno(price.getPpno())
          .pno(price.getProduct().getPno())
          .price(price.getPrice())
          .salePrice(price.getSalePrice())
          .regDate(price.getRegDate())
          .modDate(price.getModDate())
          .build();
  }
}
