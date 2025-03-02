package com.eeerrorcode.pilllaw.entity.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Table(name="tbl_product_favorite")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductFavoriteId implements Serializable {

    private Long mno;
    private Long pno;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFavoriteId that = (ProductFavoriteId) o;
        return Objects.equals(mno, that.mno) &&
               Objects.equals(pno, that.pno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mno, pno);
    }
}
