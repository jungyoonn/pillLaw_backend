package com.eeerrorcode.pilllaw.entity.product;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "tbl_product_category")
@Builder
@Getter
@AllArgsConstructor
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId pcid;  // 복합 키 사용

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pno") // 복합 키의 pno (Product ID) 매핑
    @JoinColumn(name = "pno", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cno") // 복합 키의 cno (Category ID) 매핑
    @JoinColumn(name = "cno", nullable = false)
    private Category category;
}


