package com.eeerrorcode.pilllaw.repository.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.ProductPrice;


@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long>{
  Optional<ProductPrice> findByProduct_Pno(Long pno); 
}
