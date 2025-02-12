package com.eeerrorcode.pilllaw.entity.product;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tbl_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Product extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pno;

  private String pname;
  
  private String company;
  
  private LocalDateTime bestBefore;
  
  private String effect;
  
  private String precautions;
  
  private String keep;
  
  private boolean state;
  
  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private Set<ProductType> typeSet = new HashSet<>();

  public void addProductType(ProductType pt){
    typeSet.add(pt);
  }

}
