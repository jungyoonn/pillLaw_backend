package com.eeerrorcode.pilllaw.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long>{
  @Query("SELECT p FROM Package p JOIN p.products prod WHERE prod.pno = :pno")
  List<Package> findPackagesByProductId(@Param("pno") Long pno);
}
