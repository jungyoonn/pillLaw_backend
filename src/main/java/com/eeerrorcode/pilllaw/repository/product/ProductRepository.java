package com.eeerrorcode.pilllaw.repository.product;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
  // List<Product> findByCategoryTypeIn(List<CategoryType> types);
  List<Product> findByState(boolean state); // state 필터링 메서드 추가

  @Query("""
    SELECT p.pno, p.pname, p.company, COALESCE(AVG(r.rating), 0) as avgRating
    FROM Product p
    LEFT JOIN ProductReview r ON r.product = p
    WHERE p.state = true
    GROUP BY p.pno, p.pname, p.company
    ORDER BY avgRating DESC
""")
List<Object[]> findTop3ProductsByHighestRating(Pageable pageable);

//   @Query("""
//     SELECT new com.eeerrorcode.pilllaw.dto.product.ProductWithCategoryDto(
//       new com.eeerrorcode.pilllaw.dto.product.ProductDto(
//           p.pno, p.pname, p.company, p.bestBefore, p.keep, p.effect, p.precautions, p.state, p.typeSet
//       ),
//       new com.eeerrorcode.pilllaw.dto.product.ProductPriceDto(
//           pp.ppno, p.pno, pp.price, pp.salePrice, pp.rate, pp.regDate, pp.modDate
//       ),
//       c.cname
//     )
//     FROM Product p
//     LEFT JOIN ProductPrice pp ON pp.product = p
//     LEFT JOIN ProductCategory pc ON pc.product = p
//     LEFT JOIN Category c ON pc.category = c
//     WHERE p.state = true
//   """)
// List<ProductCategory> findAllProductWithCategory();

  // @Query("""
  //   SELECT p, pp FROM Product p
  //   LEFT JOIN p.productPrices pp
  //   WHERE p.state = true
  // """)
  // List<Object[]> findAllProductWithPrice();

  
}
