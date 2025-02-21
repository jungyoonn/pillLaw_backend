package com.eeerrorcode.pilllaw.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.ProductInfoView;

@Repository
public interface ProductInfoViewRepository extends JpaRepository<ProductInfoView, Long>{
  
}
