package com.eeerrorcode.pilllaw.repository.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.board.ProductReviewLike;
import com.eeerrorcode.pilllaw.entity.id.ProductReviewLikeId;

public interface ProductReviewLikeRepository extends JpaRepository<ProductReviewLike, ProductReviewLikeId>{
  // Optional<ProductReviewLike> findById(ProductReviewLikeId id);
  List<ProductReviewLike> findByProductReview(ProductReview productReview);

}
