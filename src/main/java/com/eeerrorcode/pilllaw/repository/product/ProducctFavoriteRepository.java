package com.eeerrorcode.pilllaw.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.id.ProductFavoriteId;
import com.eeerrorcode.pilllaw.entity.product.ProductFavorite;

@Repository
public interface ProducctFavoriteRepository extends JpaRepository<ProductFavorite, ProductFavoriteId>{
    // 특정 회원이 특정 상품을 좋아요했는지 확인
    @Query("SELECT COUNT(f) > 0 FROM ProductFavorite f WHERE f.member.mno = :mno AND f.product.pno = :pno")
    boolean existsByMemberAndProduct(@Param("mno") Long mno, @Param("pno") Long pno);

    // 특정 회원이 좋아요한 모든 상품 pno 조회
    @Query("SELECT f.product.pno FROM ProductFavorite f WHERE f.member.mno = :mno")
    List<Long> findLikedProductPnosByMember(@Param("mno") Long mno);

    // 특정 상품의 좋아요 개수 반환
    long countByProduct_Pno(Long pno);
    // 안전한 null 체크 포함
    default boolean existsByIdSafe(ProductFavoriteId id) {
        if (id == null) return false;
        return existsById(id);
    }

    default long countByProductPnoSafe(Long pno) {
        if (pno == null) return 0;
        return countByProduct_Pno(pno);
    }
}
