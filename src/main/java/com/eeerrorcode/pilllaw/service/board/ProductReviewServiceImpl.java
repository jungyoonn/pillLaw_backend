package com.eeerrorcode.pilllaw.service.board;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.board.ProductReviewRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductRepository;
import com.eeerrorcode.pilllaw.service.file.FileService;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ProductReviewServiceImpl implements ProductReviewService{

  private final ProductReviewRepository productReviewRepository;

  private final ProductRepository productRepository;

  private final S3Service s3Service;

  private final FileService fileService;

  private final MemberRepository memberRepository;

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
  // @Override
  // @Transactional
  // public void register(ProductReviewDto dto) {
  //   ProductReview productReview = toEntity(dto);
  //   productReviewRepository.save(productReview);
  // }

  @Override
  @Transactional
  public void register(ProductReviewDto dto) {
    log.info("🚀 리뷰 등록 요청: pno={}, mno={}", dto.getPno(), dto.getMno());
    Product product = productRepository.findById(dto.getPno())
            .orElseThrow(() -> new NoSuchElementException("❌ 상품을 찾을 수 없습니다: " + dto.getPno()));
    Member member = memberRepository.findById(dto.getMno())
            .orElseThrow(() -> new NoSuchElementException("❌ 회원 정보를 찾을 수 없습니다: " + dto.getMno()));
    log.info("✅ 회원 및 상품 조회 완료 - mno={}, pno={}", member.getMno(), product.getPno());
    ProductReview productReview = ProductReview.builder()
            .product(product)  // FK 연결 (영속 상태 객체)
            .member(member)  // FK 연결 (영속 상태 객체)
            .content(dto.getContent())
            .rating(dto.getRating() != null ? dto.getRating() : 1)
            .count(dto.getCount() != null ? dto.getCount() : 0L)
            .build();
    // final ProductReview savedReview = productReviewRepository.save(ProductReview
    // .builder()
    //   .product(product)
    //   .member(member)
    //   .content(dto.getContent())
    //   .rating(dto.getRating() != null ? dto.getRating() : 1)
    //   .count(dto.getCount() != null ? dto.getCount() : 0L)
    // .build());
    productReviewRepository.save(productReview);
    productReviewRepository.flush();  // ✅ 즉시 DB 반영하여 `prno` 값 확인
    log.info("✅ 리뷰 저장 완료: prno={}", productReview.getPrno());
    if (dto.getFileDtos() != null && !dto.getFileDtos().isEmpty()) {
        List<File> files = dto.getFileDtos().stream()
          .map(fileDto -> File.builder()
            .uuid(fileDto.getUuid())
            .origin(fileDto.getOrigin())
            .path(fileDto.getPath())
            .fname(fileDto.getFname())
            .mime(fileDto.getMime())
            .size(fileDto.getSize())
            .ext(fileDto.getExt())
            .url(fileDto.getUrl())
            .type(FileType.REVIEW)  // 파일 타입 지정
            .productReview(productReview)  // FK 연결
            .build())
          .collect(Collectors.toList());
      log.info("✅ 파일 개수: {}", files.size());
      if (!files.isEmpty()) {
          productReview.updateFiles(files); 
          productReviewRepository.save(productReview);
      }
    }
    // return new ProductReviewDto(productReview);
  }

  @Override
  public List<ProductReviewDto> showReviewsByProduct(Long pno) {
    List<ProductReviewDto> returnList = productReviewRepository.findReviewsByProduct(pno).stream().map(this::toDto).toList();
    return returnList;
  }


  
  

}
