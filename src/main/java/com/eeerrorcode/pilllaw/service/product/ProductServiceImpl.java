package com.eeerrorcode.pilllaw.service.product;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.product.ProductCategoryDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.dto.product.ProductInfoViewDto;
import com.eeerrorcode.pilllaw.dto.product.ProductWithCategoryDto;
import com.eeerrorcode.pilllaw.entity.product.Category;
import com.eeerrorcode.pilllaw.entity.product.CategoryType;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductCategory;
import com.eeerrorcode.pilllaw.entity.product.ProductType;
import com.eeerrorcode.pilllaw.repository.product.CategoryRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductCategoryRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductInfoViewRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductRepository;

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

  // 테스트 완료!
  @Override
  public Optional<ProductDto> viewProduct(Long pno) {
    return productRepository.findById(pno).map(this::toDto);
  }

  // 테스트 완료!
  @Override
  public List<ProductDto> listAllProduct() {
    List<ProductDto> returnList = productRepository
    .findAll()
      .stream().map(this::toDto)
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
    List<Product> productList = productCategoryRepository.findProductsByCategoryNames(categoryNames);
    return productList.stream()
            .map(ProductDto::new)
            .collect(Collectors.toList());
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
    return productRepository.findAll().stream().map(product -> {
      List<ProductCategoryDto> categories = productCategoryRepository.findByProduct(product).stream().map(ProductCategoryDto::new).toList();
      return new ProductWithCategoryDto(new ProductDto(product), categories);
    }).toList();
  }
  
  
  

}
