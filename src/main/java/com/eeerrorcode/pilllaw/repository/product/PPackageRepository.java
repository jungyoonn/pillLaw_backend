package com.eeerrorcode.pilllaw.repository.product;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.product.PPackage;

@Repository
public interface PPackageRepository extends JpaRepository<PPackage, Long>{
  // @Query("SELECT p FROM PPackage p JOIN p.products prod WHERE prod.pno = :pno")
  // List<Package> findPackagesByProductId(@Param("pno") Long pno);
}
