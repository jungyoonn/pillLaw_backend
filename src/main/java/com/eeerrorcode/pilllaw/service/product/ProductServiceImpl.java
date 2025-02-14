package com.eeerrorcode.pilllaw.service.product;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductType;
import com.eeerrorcode.pilllaw.repository.product.ProductDetailRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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

  // @Override
  // @Transactional
  // public void registerProduct(ProductDto dto) {
  //   Product product = toEntity(dto);
  //   productRepository.save(product);
  //   log.info("========================================");
  //   log.info("새 상품 등록 ::: " + product.getPname());
  //   log.info("========================================");
  // // Product product = Product.builder()
  // //         .pname(dto.getPname())
  // //         .company(dto.getCompany())
  // //         .bestBefore(dto.getBestBefore())
  // //         .keep(dto.getKeep())
  // //         .effect(dto.getEffect())
  // //         .precautions(dto.getPrecautions())
  // //         .state(true)  // 기본 활성화
  // //         .build();

  // //     product = productRepository.save(product);

  // //     log.info("등록된 상품 ID (pno) ::: " + product.getPno());

  // //     dto.getType().forEach(type -> product.addProductType(ProductType.valueOf(type)));

  // //     productRepository.save(product);

  //   }
  

  @Override
@Transactional
public void registerProduct(ProductDto dto) {
    // 1️⃣ 먼저 `Product`를 저장 (pno 생성됨)
    Product product = toEntity(dto);
    product = productRepository.save(product); // 🚀 먼저 저장 후 pno 생성

    // 2️⃣ 강제적으로 DB에 반영 (`flush()` 호출)
    productRepository.flush();  // 🚀 Hibernate가 강제적으로 SQL 실행하도록 유도

    // 3️⃣ `typeSet` 추가 (pno가 이제 존재하므로 안전하게 추가 가능)
    Set<ProductType> types = dto.getType().stream()
        .map(ProductType::valueOf)
        .collect(Collectors.toSet());

    product.getTypeSet().clear(); // 혹시 기존 데이터가 있다면 초기화
    product.getTypeSet().addAll(types);

    // 4️⃣ 다시 저장 (이제 `typeSet`이 `pno`와 함께 저장됨)
    productRepository.save(product);
}

}
