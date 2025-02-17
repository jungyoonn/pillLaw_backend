package com.eeerrorcode.pilllaw.entity.product;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Product extends BaseEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pno;

  private String pname;
  private String company;
  private String bestBefore;
  @Column(length = 1000)
  private String effect;
  @Column(length = 1000)
  private String precautions;
  private String keep;
  private boolean state;
  
  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "tbl_product_type_set", joinColumns = @JoinColumn(name = "pno"))
  @Enumerated(EnumType.STRING)
  @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.PERSIST)
  private Set<ProductType> typeSet = new HashSet<>();

  public void addProductType(ProductType pt) {
    typeSet.add(pt);
  }
}
