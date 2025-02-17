package com.eeerrorcode.pilllaw.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.id.ProductCategoryId;
import com.eeerrorcode.pilllaw.entity.product.Category;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductCategory;
import java.util.List;


@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId>{
  List<ProductCategory> findByProduct(Product product);
  List<ProductCategory> findByCategory(Category category);
  
  @Query("SELECT pc.category FROM ProductCategory pc WHERE pc.product.pno = :pno")
  List<ProductCategory> findCategoriesByProduct(@Param("pno") Long pno);

  @Query("SELECT pc.product FROM ProductCategory pc WHERE pc.category = :category")
  List<Product> findProductsByCategory(@Param("category") Category category);

  @Modifying
  @Query("DELETE FROM ProductCategory pc WHERE pc.product.pno = :pno")
  void deleteByProductPno(@Param("pno") Long pno);

  @Query("SELECT pc.product FROM ProductCategory pc JOIN pc.category c WHERE c.cname IN :categoryNames")
  List<Product> findProductsByCategoryNames(@Param("categoryNames") List<String> categoryNames);

}
