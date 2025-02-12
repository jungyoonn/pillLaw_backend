package com.eeerrorcode.pilllaw.entity.product;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.id.ProductFavoriteId;
import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Entity(name = "tbl_product_favorite")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EnableJpaRepositories
public class ProductFavorite extends BaseEntity{
  
  @EmbeddedId
  private ProductFavoriteId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("mno")
  @JoinColumn(name = "mno", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("pno")
  @JoinColumn(name = "pno", nullable = false)
  private Product product;

}
