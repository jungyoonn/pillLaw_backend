package com.eeerrorcode.pilllaw.entity.product;

import com.eeerrorcode.pilllaw.entity.id.ProductPPackageId;

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


@Entity(name = "tbl_product_package")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductPPackage {
    
    @EmbeddedId
    private ProductPPackageId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pckNo")
    @JoinColumn(name = "pck_no", nullable = false)
    private PPackage pack;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pno") 
    @JoinColumn(name = "pno", nullable = false)
    private Product product;

    private Integer quantity; 
}
