package com.eeerrorcode.pilllaw.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductType;

@Service
public interface ProductService {
  
  void registerProduct(ProductDto dto);

  void modifyProduct(ProductDto dto);
  
  void deleteProduct(Long pno);
  
  Optional<ProductDto> viewProduct(Long pno);

  List<ProductDto> listAllProduct();
  
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
      .type(product.getTypeSet().stream()
        .map(Enum::name)
        .collect(Collectors.toList()))
    .build();

    return dto;
  }

}
