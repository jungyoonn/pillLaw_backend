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

@Entity(name = "tbl_category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Category extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cno;

  private String cname;

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<CategoryType> typeSet = new HashSet<>();

  public void addProductType(CategoryType ct){
    typeSet.add(ct);
  }
}
