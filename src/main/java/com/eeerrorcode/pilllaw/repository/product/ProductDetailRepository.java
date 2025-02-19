package com.eeerrorcode.pilllaw.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.ProductDetail;
import java.util.Optional;


@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>{
  
  @Query("SELECT pd FROM com.eeerrorcode.pilllaw.entity.product.ProductDetail pd WHERE pd.product.pno = :pno")
  Optional<ProductDetail> findByProduct(@Param("pno") Long pno);
  
}
