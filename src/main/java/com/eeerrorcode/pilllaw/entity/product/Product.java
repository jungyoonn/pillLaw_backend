package com.eeerrorcode.pilllaw.entity.product;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tbl_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product extends BaseEntity{
  
  @Id
  private Long pno;
  private String pname;
  private String company;
  private LocalDateTime bestBefore;
  private String effect;
  private String precautions;
  private String keep;
  // private Category cname;
  private boolean state;
  
  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<ProductType> typeSet = new HashSet<>();

  public void addProductType(ProductType pt){
    typeSet.add(pt);
  }


}
