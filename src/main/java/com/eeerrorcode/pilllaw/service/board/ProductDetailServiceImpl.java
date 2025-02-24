package com.eeerrorcode.pilllaw.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;
import com.eeerrorcode.pilllaw.repository.product.ProductDetailRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService{
  
  @Autowired
  private final ProductDetailRepository productDetailRepository;

  // 테스트 통과!!
  @Override
  public ProductDetailDto showDetailsByPno(Long pno) {
    log.info("showDetailsByPno :::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    ProductDetailDto returnDetail = toDto(productDetailRepository.findByProduct(pno).get());
    return returnDetail;
  }
  
  // 테스트 통과!!
  @Override
  public Page<ProductDetailDto> showAll(Pageable pageable) {
    log.info("showAllList :::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    Page<ProductDetailDto> returnList = productDetailRepository.findAll(pageable).map(this::toDto);
    return returnList;
  }

  @Override
  public Page<ProductDetailDto> showMyList(Pageable pageable, Long mno) {
    // 관리자
    return null;
  }

  // 테스트 통과!!
  @Override
  public void deleteDetail(Long pno) {
    productDetailRepository.deleteById(pno);
  }

  // 
  @Override
  public void modifyDetail(ProductDetailDto dto) {
    productDetailRepository.save(toEntity(dto));
  }

  // 테스트 통과!!
  @Override
  public void registerDetail(ProductDetailDto dto) {
    ProductDetail returnDetail = toEntity(dto);
    productDetailRepository.save(returnDetail);
  }

}
