package com.eeerrorcode.pilllaw.dto.product;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

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
  private LocalDateTime regDate, modDate;
  private String imageUrl;
  private List<String> imageUrlList;
  
  private List<String> type;
  private List<CategoryDto> categories;
  private ProductPriceDto priceInfo;

  public ProductDto(ProductDto product, ProductPriceDto price, List<CategoryDto> categories) {
    this.pno = product.getPno();
    this.pname = product.getPname();
    this.company = product.getCompany();
    this.bestBefore = product.getBestBefore();
    this.effect = product.getEffect();
    this.precautions = product.getPrecautions();
    this.keep = product.getKeep();
    this.state = product.isState();
    this.type = (product.getType() != null) ? product.getType() : List.of();
    this.regDate = (product.getRegDate() != null) ? product.getRegDate() : LocalDateTime.now(); // ✅ NULL 방지
    this.modDate = (product.getModDate() != null) ? product.getModDate() : LocalDateTime.now(); // ✅ NULL 방지
    this.priceInfo = price;
    this.categories = categories;
  }


}
