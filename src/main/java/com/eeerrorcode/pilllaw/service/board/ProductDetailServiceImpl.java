package com.eeerrorcode.pilllaw.service.board;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.repository.product.ProductDetailRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService{
  
  private final ProductDetailRepository productDetailRepository;

  @Override
  public ProductDetailDto showDetails(Long pno) {
    return productDetailRepository.findById(pno).map(this::toDto).orElseGet(ProductDetailDto::new);
  }


}
