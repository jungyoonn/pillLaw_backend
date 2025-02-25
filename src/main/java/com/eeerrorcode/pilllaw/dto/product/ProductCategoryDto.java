package com.eeerrorcode.pilllaw.dto.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.eeerrorcode.pilllaw.entity.product.CategoryType;
import com.eeerrorcode.pilllaw.entity.product.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCategoryDto {

  private Long pno;  // 상품 ID
  private String pname;  // 상품명 추가
  private Long cno;  // 카테고리 ID
  private String cname;  // 카테고리명
  private String categoryType;  // BIOACTIVE 또는 NUTRIENT

  public ProductCategoryDto(ProductCategory productCategory) {
    if (productCategory == null || productCategory.getProduct() == null || productCategory.getCategory() == null) {
      throw new IllegalArgumentException("ProductCategory 또는 연관 엔티티가 NULL입니다.");
    }
    this.pno = productCategory.getProduct().getPno();
    this.pname = productCategory.getProduct().getPname(); // 상품명 추가
    this.cno = productCategory.getCategory().getCno();
    this.cname = productCategory.getCategory().getCname() != null ? productCategory.getCategory().getCname() : "Unknown";
    this.categoryType = productCategory.getCategory().getTypeSet().contains(CategoryType.BIOACTIVE) ? "BIOACTIVE" : "NUTRIENT";
}

// CategoryDto로 변환하는 메서드
public Stream<CategoryDto> toCategoryDto() {
  List<String> categoryTypeList = new ArrayList<>();
  if (this.categoryType != null) {
    categoryTypeList.add(this.categoryType);
  }
  CategoryDto categoryDto = CategoryDto
  .builder()
    .cno(this.cno)
    .cname(this.cname)
    .type(categoryTypeList)  // CategoryDto의 필드명이 type임
    .products(List.of())  // 빈 products 리스트
  .build();
  return Stream.of(categoryDto);
}
}

