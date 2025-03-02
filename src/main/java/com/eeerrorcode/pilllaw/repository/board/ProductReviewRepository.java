package com.eeerrorcode.pilllaw.repository.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eeerrorcode.pilllaw.entity.board.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{
  @Query("SELECT r FROM ProductReview r WHERE r.product.pno = :pno ORDER BY r.modDate DESC")
  List<ProductReview> findReviewsByProduct(@Param("pno") Long pno);
  @Query("SELECT r FROM ProductReview r WHERE r.member.mno = :mno ORDER BY r.regDate DESC")
  List<ProductReview> findReviewsByMember(@Param("mno") Long mno);
  

}
