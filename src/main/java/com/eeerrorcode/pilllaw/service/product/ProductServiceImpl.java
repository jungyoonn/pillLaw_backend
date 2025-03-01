package com.eeerrorcode.pilllaw.service.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.product.CategoryDto;
import com.eeerrorcode.pilllaw.dto.product.ProductCategoryDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.dto.product.ProductInfoViewDto;
import com.eeerrorcode.pilllaw.dto.product.ProductWithCategoryDto;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.product.Category;
import com.eeerrorcode.pilllaw.entity.product.CategoryType;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductCategory;
import com.eeerrorcode.pilllaw.entity.product.ProductPrice;
import com.eeerrorcode.pilllaw.entity.product.ProductType;
import com.eeerrorcode.pilllaw.repository.product.CategoryRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductCategoryRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductInfoViewRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductPriceRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductRepository;
import com.eeerrorcode.pilllaw.service.board.ProductReviewService;
import com.eeerrorcode.pilllaw.service.file.FileService;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
  
  private final ProductRepository productRepository;

  private final CategoryRepository categoryRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final ProductInfoViewRepository productInfoViewRepository;

  private final ProductPriceRepository productPriceRepository;

  private final ProductPriceService productPriceService;

  private final S3Service s3Service;

  private final ProductReviewService productReviewService;

  private final FileService fileService;

  // 테스트 완료!
  @Override
  public Optional<ProductDto> viewProduct(Long pno) {
    return productRepository.findById(pno)
      .map(this::toDto)
      .map(dto -> {
        // 가격 정보 설정
        productPriceRepository.findByProductPno(pno)
            .ifPresent(p -> dto.setPriceInfo(productPriceService.toDto(p)));
        
        // 이미지 URL 설정 - 디버깅 로그 추가
        try {
            String returnUUID = fileService.getFirstUUIDByPNO(pno);
            log.info("UUID: {}", returnUUID);
            List<String> returnUUIDList = fileService.getImageListByPno(pno);
            log.info("UUIDLIST : {}", returnUUIDList);
            String imageUrl = s3Service.generateProductImageUrl(pno, returnUUID);
            List<String> imageUrlList = returnUUIDList.stream().map(ul -> s3Service.generateProductImageUrl(pno, ul)).toList();
            log.info("생성된 이미지 URL 리스트: {}", imageUrlList);
            
            dto.setImageUrl(imageUrl);
            dto.setImageUrlList(imageUrlList);
            log.info("DTO 전체 목록 확인 : {}", dto);
            log.info("DTO에 이미지 URL 설정: {}", dto.getImageUrl());
            log.info("DTO에 이미지 URL 리스트 설정 : {}", dto.getImageUrlList());
        } catch (Exception e) {
            log.error("이미지 정보를 가져오는 중 오류 발생: " + e.getMessage(), e);
        }
        
        return dto;
      });
  }
  
  

  // 테스트 완료!
  @Override
  public List<ProductDto> listAllProduct() {
    List<ProductDto> returnList = productRepository.findAll()
    .stream()
    .map(this::toDto)  // Product -> ProductDto 변환
    .peek(dto -> dto.setImageUrl(fileService.getFirstUUIDByPNO(dto.getPno()))) // 대표 이미지 설정
    .toList();
    return returnList;
  }

  // 테스트 완료!
  @Override
  public void deleteProduct(Long pno) {
    productRepository.deleteById(pno);    
  }


  // 테스트 완료!
  @Override
  public void modifyProduct(ProductDto dto) {
    Product product = toEntity(dto);

    List<ProductCategory> existingCategories = productCategoryRepository.findByProduct(product);
    productCategoryRepository.deleteAll(existingCategories);

    List<ProductCategory> newCategories = dto.getType().stream() 
        .map(typeName -> categoryRepository.findByCname(typeName) 
            .map(category -> new ProductCategory(product, category)) 
            .orElse(null)) 
        .filter(Objects::nonNull) 
        .toList();

    productCategoryRepository.saveAll(newCategories);
    productRepository.save(product);
  }


  
  // 테스트 완료!
  @Override
  @Transactional
  public void registerProduct(ProductDto dto) {
    Product product = toEntity(dto);
    product = productRepository.save(product); 
    productRepository.flush();  
    Set<ProductType> types = dto.getType().stream()
        .map(ProductType::valueOf)
        .collect(Collectors.toSet());
    product.getTypeSet().clear(); 
    product.getTypeSet().addAll(types);
    productRepository.save(product);
  }

  
  
  // 테스트 
  @Override
  public List<ProductDto> listProductByCategoryNameList(List<String> categoryNames) {
    List<ProductDto> productList = productCategoryRepository.findProductsByCategoryNames(categoryNames).stream().map(this::toDto).toList();
    return productList;
}

  @Override
  public List<ProductDto> listProductByCategory(List<CategoryType> types) {
  //   List<Product> products = productRepository.findByCategoryTypeIn(types);
  //   return products.stream()
  //           .map(ProductDto::new)
  //           .collect(Collectors.toList());
    return null;
  }

  // 테스트 완료!
  @Override
  public List<ProductDto> listProductByCategoryName(String categoryName) {
      Category category = categoryRepository.findByCname(categoryName)
          .orElseThrow(() -> new RuntimeException("카테고리 없음::: " + categoryName));
  
      List<ProductDto> productList = productCategoryRepository.findByCategory(category)
          .stream()
          .map(pc -> this.toDto(pc.getProduct()))
          .toList();
  
      return productList;
  }
  
  // 테스트 완료!
  @Override
  public List<ProductDto> listProductByCategoryNameAndCategoryType(Set<String> categoryNames, Set<String> categoryTypes) {
    Set<Category> categories = new HashSet<>(categoryRepository.findByCnameIn(categoryNames));

    List<ProductDto> productList = productCategoryRepository.findByCategoryIn(categories)
        .stream()
        .filter(pc -> !Collections.disjoint(pc.getCategory().getTypeSet(), categoryTypes))
        .map(pc -> this.toDto(pc.getProduct()))
        .toList();

    return productList;
  }
  

  @Override
  public Optional<ProductInfoViewDto> viewProductUsingView(Long pno) {
    return productInfoViewRepository
      .findById(pno)
      .map(ProductInfoViewDto::new);
  }

  @Override
  public List<ProductInfoViewDto> listAllProductUsingView() {
    return productInfoViewRepository
      .findAll()
      .stream()
      .map(ProductInfoViewDto::new)
      .toList();
  }

  @Override
  public List<ProductWithCategoryDto> listAllProductWithCategory() {
    return productRepository.findByState(true).stream()
      .map(product -> {
        List<ProductCategoryDto> categories = productCategoryRepository.findByProduct(product)
          .stream()
          .map(ProductCategoryDto::new)
          .toList();

        ProductPrice price = productPriceRepository.findByProductPno(product.getPno())
          .orElse(null);

        List<CategoryDto> categoryDtos = categories.stream().map(c -> {
          List<String> categoryTypeList = new ArrayList<>();
          if (c.getCategoryType() != null) {
            categoryTypeList.add(c.getCategoryType());
          }
          return CategoryDto.builder()
            .cno(c.getCno())
            .cname(c.getCname())
            .type(categoryTypeList)
            .build();
        }).toList();

        List<ProductReviewDto> reviews;
        try {
          log.info("📢 리뷰 조회 시작: PNO: {}", product.getPno());
          reviews = productReviewService.showReviewsByProduct(product.getPno());
          log.info("📢 리뷰 개수: {} | PNO: {}", reviews.size(), product.getPno());
        } catch (Exception e) {
          log.error("❌ 리뷰 조회 중 오류 발생! PNO: {} | 오류 메시지: {}", product.getPno(), e.getMessage(), e);
          reviews = Collections.emptyList();
        }

        ProductDto productDto = new ProductDto(toDto(product), productPriceService.toDto(price), categoryDtos);

        String imageUUID = fileService.getFirstUUIDByPNO(product.getPno());
        String imageUrl = (imageUUID != null) ? s3Service.generateProductImageUrl(product.getPno(), imageUUID) : null;
        productDto.setImageUrl(imageUrl);

        log.info("대표 이미지 설정: PNO: {}, UUID: {}, URL: {}", product.getPno(), imageUUID, imageUrl);

        return new ProductWithCategoryDto(
            productDto,
            productPriceService.toDto(price),
            categoryDtos,  
            reviews);
      })
      .toList();
  }
  


  
  
  
  


  
  
  

}
