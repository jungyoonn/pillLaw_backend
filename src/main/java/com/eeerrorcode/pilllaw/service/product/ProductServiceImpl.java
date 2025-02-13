package com.eeerrorcode.pilllaw.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;
import com.eeerrorcode.pilllaw.entity.product.ProductType;
import com.eeerrorcode.pilllaw.repository.product.ProductDetailRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
  
  private ProductRepository productRepository;

  private final ProductDetailRepository productDetailRepository;


  @Override
  public Optional<ProductDto> viewProduct(Long pno) {
    return productRepository.findById(pno).map(this::toDto);
  }

  @Override
  public List<ProductDto> listAllProduct() {
    List<ProductDto> returnList = productRepository
    .findAll()
      .stream().map(this::toDto)
    .toList();
    return returnList;
  }

  @Override
  public void deleteProduct(Long pno) {
    productRepository.deleteById(pno);    
  }

  @Override
  public void modifyProduct(ProductDto dto) {
    Product product = toEntity(dto);
    // 카테고리 연관 맵핑되어 있는 것을 수정해야 하므로, productCategoryRepository 호출??
    // 상세정보 연관 맵핑되어 있는 것을 수정해야 하므로, productDetailRepository 호출?
    productRepository.save(product);
  }

  @Override
  @Transactional
  public void registerProduct(ProductDto dto) {
    Product product = toEntity(dto);
    productRepository.save(product);
    log.info("========================================");
    log.info("새 상품 등록 ::: " + product.getPname());
    log.info("========================================");
  // Product product = Product.builder()
  //         .pname(dto.getPname())
  //         .company(dto.getCompany())
  //         .bestBefore(dto.getBestBefore())
  //         .keep(dto.getKeep())
  //         .effect(dto.getEffect())
  //         .precautions(dto.getPrecautions())
  //         .state(true)  // 기본 활성화
  //         .build();

  //     product = productRepository.save(product);

  //     log.info("등록된 상품 ID (pno) ::: " + product.getPno());

  //     dto.getType().forEach(type -> product.addProductType(ProductType.valueOf(type)));

  //     productRepository.save(product);

    }
  
}
