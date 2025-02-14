package com.eeerrorcode.pilllaw.entity.product;

import com.eeerrorcode.pilllaw.entity.id.ProductCategoryId;

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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_product_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId pcid;  

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pno") 
    @JoinColumn(name = "pno", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cno") 
    @JoinColumn(name = "cno", nullable = false)
    private Category category;

    public ProductCategory(Product product, Category category) {
        this.pcid = new ProductCategoryId(product.getPno(), category.getCno()); // 복합키 설정
        this.product = product;
        this.category = category;
    }
}


