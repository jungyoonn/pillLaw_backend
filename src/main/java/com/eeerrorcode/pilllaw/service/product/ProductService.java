package com.eeerrorcode.pilllaw.service.product;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.dto.product.ProductInfoViewDto;
import com.eeerrorcode.pilllaw.dto.product.ProductWithCategoryDto;
import com.eeerrorcode.pilllaw.entity.product.CategoryType;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductType;

public interface ProductService {
  
  void registerProduct(ProductDto dto);

  void modifyProduct(ProductDto dto);
  
  void deleteProduct(Long pno);
  
  Optional<ProductDto> viewProduct(Long pno);

  List<ProductDto> listAllProduct();

  List<ProductDto> listProductByCategory(List<CategoryType> types);

  List<ProductDto> listProductByCategoryNameList(List<String> names);

  List<ProductDto> listProductByCategoryName(String Category);

  List<ProductDto> listProductByCategoryNameAndCategoryType(Set<String> categoryNames, Set<String> categoryTypes);

  Optional<ProductInfoViewDto> viewProductUsingView(Long pno);
  
  List<ProductInfoViewDto> listAllProductUsingView();

  public List<ProductWithCategoryDto> listAllProductWithCategory();

  default Product toEntity(ProductDto dto){
    Product product = Product
      .builder()
        .pno(dto.getPno())
        .pname(dto.getPname())
        .company(dto.getCompany())
        .bestBefore(dto.getBestBefore())
        .keep(dto.getKeep())
        .effect(dto.getEffect())
        .precautions(dto.getPrecautions())
        .state(dto.isState())
        .typeSet(dto
          .getType()
          .stream()
          .map(ProductType::valueOf)
          .collect(Collectors.toSet()))
      .build();

    return product;
  }

  default ProductDto toDto(Product product){
    ProductDto dto =  ProductDto
    .builder()
      .pno(product.getPno())
      .pname(product.getPname())
      .company(product.getCompany())
      .bestBefore(product.getBestBefore())
      .keep(product.getKeep())
      .effect(product.getEffect())
      .precautions(product.getPrecautions())
      .regDate(product.getRegDate())
      .modDate(product.getModDate())
      .state(product.isState())
      .type(product.getTypeSet().stream()
        .map(Enum::name)
        .collect(Collectors.toList()))
    .build();

    return dto;
  }

}
