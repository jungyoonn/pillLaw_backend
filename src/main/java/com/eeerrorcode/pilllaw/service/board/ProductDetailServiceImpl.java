package com.eeerrorcode.pilllaw.service.board;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;
import com.eeerrorcode.pilllaw.repository.product.ProductDetailRepository;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

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

  @Autowired
  private final S3Service s3service;

  // 테스트 통과!!
  @Override
  public ProductDetailDto showDetailsByPno(Long pno) {
    log.info("showDetailsByPno :::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    
    ProductDetail productDetail = productDetailRepository.findByProduct(pno)
        .orElseThrow(() -> new NoSuchElementException("❌ 해당 제품의 상세 정보를 찾을 수 없습니다: " + pno));

    List<FileDto> fileDtos = productDetail.getFiles().stream()
      .map(FileDto::new)
      .collect(Collectors.toList());

    return ProductDetailDto.builder()
      .pdno(productDetail.getPdno())
      .pno(productDetail.getProduct().getPno())
      .content(productDetail.getContent())
      .count(productDetail.getCount())
      .mno(productDetail.getMember().getMno())
      .detailUrl(null)
      .fileDtos(fileDtos) // ✅ 파일 DTO 리스트 추가
      .build();
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
