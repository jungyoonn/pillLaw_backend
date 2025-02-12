package com.eeerrorcode.pilllaw.entity.product;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductCategoryId implements Serializable {

    private Long pno;
    private Long cno;

    public ProductCategoryId() {}

    public ProductCategoryId(Long pno, Long cno) {
        this.pno = pno;
        this.cno = cno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryId that = (ProductCategoryId) o;
        return Objects.equals(pno, that.pno) &&
               Objects.equals(cno, that.cno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pno, cno);
    }
}
