package com.eeerrorcode.pilllaw.service.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.repository.board.ProductReviewRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ProductReviewServiceImpl implements ProductReviewService{

  private final ProductReviewRepository productReviewRepository;

  // 테스트 통과!
  @Override
  public List<ProductReviewDto> showReviews() {
    List<ProductReviewDto> returnList = productReviewRepository
    .findAll()
      .stream().map(this::toDto)
    .toList();
    return returnList;
  }

  // 테스트 통과!
  @Override
  public void delete(Long prno) {
    productReviewRepository.deleteById(prno);    
  }

  // 테스트 통과!
  @Override
  public void modify(ProductReviewDto dto) {
    ProductReview review = productReviewRepository.findById(dto.getPrno()).orElseThrow(()-> new IllegalArgumentException("Review Not found"));

    List<File> newFiles = dto.getFileDtos().stream()
    .map(a -> File.builder()
        .uuid(a.getUuid())
        .origin(a.getOrigin())
        .path(a.getPath())
        .fname(a.getFname())
        .mime(a.getMime())
        .size(a.getSize())
        .ext(a.getExt())
        .url(a.getUrl())
        .productReview(review)
        .type(FileType.REVIEW)
        .build())
    .toList();

    review.updateReview(dto.getContent(), dto.getRating());
    review.updateFiles(newFiles);
  }

  // 테스트 통과!!
  @Override
  public void register(ProductReviewDto dto) {
    ProductReview productReview = toEntity(dto);
    productReviewRepository.save(productReview);
  }

  // 테스트 통과!!
  @Override
  public List<ProductReviewDto> showReviewsByProduct(Long pno) {
    List<ProductReviewDto> returnList = productReviewRepository.findReviewsByProduct(pno).stream().map(this::toDto).toList();
    return returnList;
  }

  
  

}
