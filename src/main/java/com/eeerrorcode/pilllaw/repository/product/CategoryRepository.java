package com.eeerrorcode.pilllaw.repository.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
  Optional<Category> findByCname(String cname);
}
