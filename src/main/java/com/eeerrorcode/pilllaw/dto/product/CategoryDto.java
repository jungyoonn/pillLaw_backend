package com.eeerrorcode.pilllaw.dto.product;

import java.util.List;
import java.util.stream.Collectors;

import com.eeerrorcode.pilllaw.entity.product.Category;

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
public class CategoryDto {
  
  private Long cno;

  private String cname;

  private List<ProductDto> products;
  private List<String> type;

  public CategoryDto(Category category) {
    this.cno = category.getCno();
    this.cname = category.getCname();
    this.type = category.getTypeSet().stream().map(Enum::name).collect(Collectors.toList());
    this.products = List.of();
  }

}
