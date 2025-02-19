package com.eeerrorcode.pilllaw.repository.product;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
  // List<Product> findByCategoryTypeIn(List<CategoryType> types);
}
