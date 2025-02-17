package com.eeerrorcode.pilllaw.dto.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.eeerrorcode.pilllaw.entity.product.Product;

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
public class ProductDto {
  
  private Long pno;
  private String pname;
  private String company;
  private String bestBefore;  
  private String effect;
  private String precautions;
  private String keep;
  private boolean state;

  private List<String> type;
  private List<CategoryDto> categories;

  private LocalDateTime regDate, modDate;

    public ProductDto(Product product) {
    this.pno = product.getPno();
    this.pname = product.getPname();
    this.company = product.getCompany();
    this.bestBefore = product.getBestBefore();
    this.effect = product.getEffect();
    this.precautions = product.getPrecautions();
    this.keep = product.getKeep();
    this.state = product.isState();
    this.type = (product.getTypeSet() != null) ? product.getTypeSet().stream().map(Enum::name).collect(Collectors.toList()) : List.of();
    // this.categories = List.of();
    this.regDate = product.getRegDate();
    this.modDate = product.getModDate();
  }
}
