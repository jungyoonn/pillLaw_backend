package com.eeerrorcode.pilllaw.repository.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.board.ProductReviewLike;
import com.eeerrorcode.pilllaw.entity.id.ProductReviewLikeId;

public interface ProductReviewLikeRepository extends JpaRepository<ProductReviewLike, ProductReviewLikeId> {
  List<ProductReviewLike> findById_Prno(Long prno);

  Optional<ProductReviewLike> findById_MnoAndId_Prno(Long mno, Long prno);

  Long countById_Prno(Long prno);

  Long countById_Mno(Long mno);

  boolean existsById_MnoAndId_Prno(Long mno, Long prno);

  Long countByProductReview_Prno(Long prno);

}
