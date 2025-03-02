package com.eeerrorcode.pilllaw.service.product;

import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDto;
import com.eeerrorcode.pilllaw.entity.id.ProductFavoriteId;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductFavorite;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.product.ProducctFavoriteRepository;
import com.eeerrorcode.pilllaw.repository.product.ProductRepository;
import com.eeerrorcode.pilllaw.service.file.FileService;
import com.eeerrorcode.pilllaw.service.s3.S3Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductFavoriteService {

  private final ProducctFavoriteRepository productFavoriteRepository;
  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  private final FileService fileService;
  private final S3Service s3Service;

  @Transactional
  public boolean likeProduct(Long mno, Long pno) {
    Member member = memberRepository.findById(mno)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    Product product = productRepository.findById(pno)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

    if (productFavoriteRepository.existsByMemberAndProduct(mno, pno)) {
      log.warn("❗ 이미 좋아요한 상품입니다. mno={}, pno={}", mno, pno);
      return false;
    }

    ProductFavorite favorite = ProductFavorite.builder()
        .id(new ProductFavoriteId(mno, pno))
        .member(member)
        .product(product)
        .build();

    productFavoriteRepository.save(favorite);
    log.info("✅ 좋아요 완료: mno={}, pno={}", mno, pno);
    return true;
  }

  @Transactional
  public boolean unlikeProduct(Long mno, Long pno) {
    ProductFavoriteId id = new ProductFavoriteId(mno, pno);
    if (!productFavoriteRepository.existsByMemberAndProduct(mno, pno)) {
      log.warn("❌ 좋아요한 기록이 없습니다. mno={}, pno={}", mno, pno);
      return false;
    }

    productFavoriteRepository.deleteById(id);
    log.info("✅ 좋아요 취소 완료: mno={}, pno={}", mno, pno);
    return true;
  }

  public boolean isProductLikedByUser(Long mno, Long pno) {
    return productFavoriteRepository.existsByMemberAndProduct(mno, pno);
  }

  public long countProductLikes(Long pno) {
    return productFavoriteRepository.countByProduct_Pno(pno);
  }

  public List<ProductDto> getLikedProducts(Long mno) {
    List<Long> likedProductPnos = productFavoriteRepository.findLikedProductPnosByMember(mno);

    return likedProductPnos.stream()
        .map(pno -> productRepository.findById(pno)
            .map(product -> {
              String imageUUID = fileService.getFirstUUIDByPNO(product.getPno());
              String imageUrl = (imageUUID != null)
                  ? s3Service.generateProductImageUrl(product.getPno(), imageUUID)
                  : null;

              log.info("product: PNO: {}, NAME: {}, IMAGE: {}", product.getPno(), product.getPname(), imageUrl);

              return ProductDto.builder()
                  .pno(product.getPno())
                  .pname(product.getPname()) // 상품명
                  .imageUrl(imageUrl) // 대표 이미지
                  .build();
            })
            .orElse(null)) 
        .filter(Objects::nonNull) 
        .toList();
  }

}
