package com.eeerrorcode.pilllaw.entity.product;

import java.util.ArrayList;
import java.util.List;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity(name = "tbl_product_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductDetail extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pdno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pno", nullable = false)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno", nullable = false)
  private Member member;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Long count;

  @Default
  @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<File> files = new ArrayList<>();
}
