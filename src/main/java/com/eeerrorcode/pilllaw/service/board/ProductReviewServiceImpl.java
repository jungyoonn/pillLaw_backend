package com.eeerrorcode.pilllaw.service.board;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.board.ProductReviewLikeDto;
import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.board.ProductReviewLike;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.entity.id.ProductReviewLikeId;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.board.ProductReviewLikeRepository;
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
public class ProductReviewServiceImpl implements ProductReviewService {

  private final ProductReviewRepository productReviewRepository;

  private final ProductRepository productRepository;

  private final S3Service s3Service;

  private final FileService fileService;

  private final MemberRepository memberRepository;

  private final ProductReviewLikeRepository productReviewLikeRepository;

  // 테스트 통과!
  @Override
  public List<ProductReviewDto> showReviews() {
    List<ProductReviewDto> returnList = productReviewRepository
        .findAll()
        .stream().map(this::toDto)
        .toList();
    return returnList;
  }

  @Override
  @Transactional
  public void addLike(ProductReviewLikeDto dto) {
    log.info("✅ 좋아요 추가 요청 - mno={}, prno={}", dto.getMno(), dto.getPrno());

    // ✅ 회원 및 리뷰 엔티티 조회
    Member member = memberRepository.findById(dto.getMno())
        .orElseThrow(() -> new NoSuchElementException("❌ 회원을 찾을 수 없습니다: mno=" + dto.getMno()));

    ProductReview productReview = productReviewRepository.findById(dto.getPrno())
        .orElseThrow(() -> new NoSuchElementException("❌ 리뷰를 찾을 수 없습니다: prno=" + dto.getPrno()));

    // ✅ 중복 체크
    ProductReviewLikeId likeId = new ProductReviewLikeId(dto.getMno(), dto.getPrno());
    if (productReviewLikeRepository.existsById(likeId)) {
      log.warn("⚠️ 이미 좋아요를 눌렀습니다. - mno={}, prno={}", dto.getMno(), dto.getPrno());
      return;
    }

    // ✅ 회원과 리뷰 객체를 포함한 좋아요 객체 생성
    ProductReviewLike like = ProductReviewLike.builder()
        .id(likeId)
        .member(member) // ✅ 반드시 넣어야 함
        .productReview(productReview) // ✅ 반드시 넣어야 함
        .build();

    productReviewLikeRepository.save(like);
    log.info("👍 좋아요 추가 완료 - mno={}, prno={}", dto.getMno(), dto.getPrno());
  }

  @Override
  public void removeLike(ProductReviewLikeDto dto) {
    ProductReviewLikeId likeId = new ProductReviewLikeId(dto.getMno(), dto.getPrno());
    Optional<ProductReviewLike> like = productReviewLikeRepository.findById(likeId);
    if (like.isPresent()) {
      productReviewLikeRepository.delete(like.get());
      log.info("❌ 좋아요 취소 완료 - mno={}, prno={}", dto.getMno(), dto.getPrno());
    } else {
      log.warn("⚠️ 좋아요 기록이 없습니다. - mno={}, prno={}", dto.getMno(), dto.getPrno());
    }
  }

  @Override
  public boolean isLikedByMember(Long mno, Long prno) {
    ProductReviewLikeId likeId = new ProductReviewLikeId(mno, prno);
    return productReviewLikeRepository.existsById(likeId);
  }

  // 테스트 통과!
  @Override
  public void delete(Long prno) {
    productReviewRepository.deleteById(prno);
  }

  // 테스트 통과!
  @Override
  public void modify(ProductReviewDto dto) {
    ProductReview review = productReviewRepository.findById(dto.getPrno())
        .orElseThrow(() -> new IllegalArgumentException("Review Not found"));

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
  // ProductReview productReview = toEntity(dto);
  // productReviewRepository.save(productReview);
  // }

  @Override
  @Transactional
  public Long register(ProductReviewDto dto) {
    log.info("🚀 리뷰 등록 요청: pno={}, mno={}", dto.getPno(), dto.getMno());
    try {
      Product product = productRepository.findById(dto.getPno())
          .orElseThrow(() -> new NoSuchElementException("❌ 상품을 찾을 수 없습니다: " + dto.getPno()));
      Member member = memberRepository.findById(dto.getMno())
          .orElseThrow(() -> new NoSuchElementException("❌ 회원 정보를 찾을 수 없습니다: " + dto.getMno()));
      log.info("회원 및 상품 조회 완료 - mno={}, pno={}", member.getMno(), product.getPno());
      ProductReview productReview = ProductReview.builder()
          .product(product) // FK 연결 (영속 상태 객체)
          .member(member) // FK 연결 (영속 상태 객체)
          .content(dto.getContent())
          .rating(dto.getRating() != null ? dto.getRating() : 1)
          .count(dto.getCount() != null ? dto.getCount() : 0L)
          .build();

      productReviewRepository.save(productReview);
      productReviewRepository.flush(); // ✅ 즉시 DB 반영하여 `prno` 값 확인
      log.info(" 리뷰 저장 완료: prno={}", productReview.getPrno());
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
                .type(FileType.REVIEW) // 파일 타입 지정
                .productReview(productReview) // FK 연결
                .build())
            .collect(Collectors.toList());
        log.info("파일 개수: {}", files.size());
        if (!files.isEmpty()) {
          productReview.updateFiles(files);
          productReviewRepository.save(productReview);
        }
      }
      return productReview.getPrno();
    } catch (Exception e) {
      log.error("리뷰 등록 중 예외 발생: {}", e.getMessage(), e);
      throw e;
    }
    // return new ProductReviewDto(productReview);
  }

  @Override
  public Long countLikes(Long prno) {
    return productReviewLikeRepository.countByProductReview_Prno(prno);
  }

  @Override
  public List<ProductReviewDto> showReviewsByProduct(Long pno) {
    log.info("📌 showReviewsByProduct 실행: PNO: {}", pno);

    List<ProductReview> reviews = productReviewRepository.findReviewsByProduct(pno);

    if (reviews == null || reviews.isEmpty()) {
      log.warn("⚠️ 리뷰 없음: PNO: {}", pno);
      return Collections.emptyList();
    }

    log.info("📌 리뷰 개수: {} | PNO: {}", reviews.size(), pno);

    return reviews.stream()
        .map(review -> {
          List<FileDto> fileDtos = fileService.getFilesByReviewId(review.getPrno());

          log.info("📌 리뷰 ID: {} - 파일 개수: {}", review.getPrno(), fileDtos.size());

          return ProductReviewDto
              .builder()
              .prno(review.getPrno())
              .pno(review.getProduct().getPno())
              .mno(review.getMember().getMno())
              .nickName(review.getMember().getNickname())
              .content(review.getContent())
              .rating(review.getRating())
              .count(review.getCount())
              .fileDtos(fileDtos)
              .regDate(review.getRegDate())
              .build();
        })
        .collect(Collectors.toList());
  }

  public List<ProductReviewDto> getReviewsByMember(Long mno) {
    log.info(" getReviewsByMember 호출 - mno = {}", mno);
    try {
      return productReviewRepository.findReviewsByMember(mno).stream()
          .map(review -> {
            log.info(" 리뷰 데이터 확인 - pno: {}, 닉네임: {}, 내용: {}",
                review.getProduct().getPno(),
                review.getMember().getNickname(),
                review.getContent() == null ? "NULL" : review.getContent());

            return ProductReviewDto.builder()
                .pno(review.getProduct().getPno())
                .nickName(review.getMember().getNickname())
                .regDate(review.getRegDate())
                .content(review.getContent() != null ? review.getContent() : "내용 없음")
                .build();
          })
          .collect(Collectors.toList());
    } catch (Exception e) {
      log.error(" 리뷰 가져오기 중 오류 발생: {}", e.getMessage(), e);
      throw new RuntimeException("리뷰 조회 실패", e);
    }
  }

}
