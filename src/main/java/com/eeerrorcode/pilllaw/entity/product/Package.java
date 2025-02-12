package com.eeerrorcode.pilllaw.entity.product;

import java.util.HashSet;
import java.util.Set;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tbl_package")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Package extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pckNo;

  private String pckName;

  private Long salePrice;

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<PackageType> typeSet = new HashSet<>();

  public void addProductType(PackageType pt){
    typeSet.add(pt);
  }

}
