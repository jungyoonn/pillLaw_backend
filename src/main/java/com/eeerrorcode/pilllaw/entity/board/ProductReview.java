package com.eeerrorcode.pilllaw.entity.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;


@Entity
@Table(name = "tbl_product_review")  // @Entity(name = "...") 대신 @Table 사용
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductReview extends BaseEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long prno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pno")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno")
  private Member member;

  @Lob  
  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Integer rating;

  @Column(nullable = false)
  private Long count;

  @Default
  @OneToMany(mappedBy = "productReview", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<File> files = new ArrayList<>();

  @PrePersist
  public void prePersist() {
    this.rating = (this.rating == null || this.rating < 1 || this.rating > 5) ? 1 : this.rating;
    this.count = (this.count == null) ? 0 : this.count;
  }

  public void updateReview(String content, int rating) {
    this.content = content;
    this.rating = rating;
  }

  public void updateFiles(List<File> newFiles) {
    this.files.clear();
    this.files.addAll(newFiles);
  }
}