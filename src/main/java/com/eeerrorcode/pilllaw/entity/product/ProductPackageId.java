package com.eeerrorcode.pilllaw.entity.product;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductPackageId implements Serializable {

    private Long pckNo;
    private Long pno;

    public ProductPackageId() {}

    public ProductPackageId(Long pckNo, Long pno) {
        this.pckNo = pckNo;
        this.pno = pno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPackageId that = (ProductPackageId) o;
        return Objects.equals(pckNo, that.pckNo) &&
               Objects.equals(pno, that.pno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pckNo, pno);
    }
}

