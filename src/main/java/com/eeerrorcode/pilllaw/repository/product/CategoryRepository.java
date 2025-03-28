package com.eeerrorcode.pilllaw.repository.product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.Category;
import com.eeerrorcode.pilllaw.entity.product.CategoryType;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
  Optional<Category> findByCname(String cname);

  @Query("SELECT DISTINCT c FROM tbl_category c JOIN c.typeSet t WHERE t = :type")
  List<Category> findByType(@Param("type") CategoryType type);
  
  List<Category> findByCnameIn(Set<String> cnames);

  @Query("SELECT c FROM tbl_category c LEFT JOIN FETCH c.typeSet WHERE c.cno = :cno")
  Optional<Category> findCategoryWithTypes(@Param("cno") Long cno);

}
