package com.eeerrorcode.pilllaw.entity.product;

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

@Entity(name = "tbl_package")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PPackage extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pckNo;

  private String pckName;

  private Long salePrice;

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private Set<PPackageType> typeSet = new HashSet<>();

  public void addProductType(PPackageType pt){
    typeSet.add(pt);
  }

}
