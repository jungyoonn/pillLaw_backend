package com.eeerrorcode.pilllaw.entity.file;
import java.util.UUID;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.board.Notice;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_file")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class File extends BaseEntity{
  
  @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
  private String uuid;

  @Column(nullable = false)
  private String origin;
  
  @Column(nullable = false)
  private String path;

  @Column(nullable = false)
  private String fname;

  @Column(nullable = false)
  private String mime;

  @Column(nullable = false)
  private Long size;

  @Column(nullable = false)
  private String ext;

  @Column(nullable = false)
  private String url;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileType type;  

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pdno")
  private ProductDetail productDetail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "prno")
  private ProductReview productReview;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nno")
  private Notice notice;

  @PrePersist
  public void prePersistMakeUUID() {
    this.uuid = (this.uuid == null) ? UUID.randomUUID().toString() : this.uuid;
  }
  
}
