package com.eeerrorcode.pilllaw.repository.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eeerrorcode.pilllaw.entity.board.Notice;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;

public interface FileRepository extends JpaRepository<File, String>{

  // 특정 ProductDetail에 연결된 파일 조회
  List<File> findByProductDetail(ProductDetail productDetail);

  // 특정 ProductReview에 연결된 파일 조회
  List<File> findByProductReview(ProductReview productReview);

  // 특정 Notice에 연결된 파일 조회
  List<File> findByNotice(Notice notice);

  List<File> findByProductDetail_PdnoOrderByFnameAsc(Long pdno);
  
  @Query(value = "SELECT UUID FROM tbl_file WHERE pno = :pno ORDER BY fname ASC", nativeQuery = true)
  List<String> findFilesByPno(@Param("pno") Long pno);
}
