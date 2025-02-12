package com.eeerrorcode.pilllaw.entity.file;
import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.board.Notice;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tbl_file")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class File extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String uuid;

  private String origin;
  
  private String path;

  private String fname;

  private String mime;

  private Long size;

  private String ext;

  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pdno")
  private ProductDetail productDetail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "prno")
  private ProductReview productReview;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "prno")
  private Notice notice;
  
}
