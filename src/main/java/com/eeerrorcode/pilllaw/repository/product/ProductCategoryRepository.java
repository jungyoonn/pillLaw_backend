package com.eeerrorcode.pilllaw.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eeerrorcode.pilllaw.entity.id.ProductCategoryId;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductCategory;
import java.util.List;
import java.util.Locale.Category;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId>{
  List<ProductCategory> findByProduct(Product product);
  List<ProductCategory> findByCategory(Category category);
  
  @Query("SELECT pc.category FROM ProductCategory pc WHERE pc.product.pno = :pno")
  List<ProductCategory> findCategoriesByProduct(@Param("pno") Long pno);
}
