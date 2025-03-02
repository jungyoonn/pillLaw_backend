package com.eeerrorcode.pilllaw.service.board;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.product.Product;

public interface ProductReviewService {
  
  List<ProductReviewDto> showReviews();

  Long register(ProductReviewDto dto);

  void delete(Long prno);

  void modify(ProductReviewDto dto);

  List<ProductReviewDto> getReviewsByMember(Long mno);

  List<ProductReviewDto> showReviewsByProduct(Long pno);

  default ProductReview toEntity(ProductReviewDto dto) {
    ProductReview productReview = ProductReview
    .builder()
      .prno(dto.getPrno())
      .product(Product.builder().pno(dto.getPno()).build())
      .member(Member.builder().mno(dto.getMno()).build())
      .content(dto.getContent())
      .rating(dto.getRating())
      .count(dto.getCount())
    .build();

    if (dto.getFileDtos() != null) {
      dto.getFileDtos().forEach(fileDto -> {
        File file = File
        .builder()
          .uuid(fileDto.getUuid())
          .origin(fileDto.getOrigin())
          .path(fileDto.getPath())
          .fname(fileDto.getFname())
          .mime(fileDto.getMime())
          .size(fileDto.getSize())
          .ext(fileDto.getExt())
          .url(fileDto.getUrl())
          .productReview(productReview) 
          .type(FileType.REVIEW)
        .build();
        productReview.getFiles().add(file);
      });
    }

      return productReview;
  }

  default ProductReviewDto toDto(ProductReview productReview) {
      List<FileDto> fileDtos = productReview.getFiles().stream()
        .map(file -> new FileDto(
          file.getUuid(),
          file.getOrigin(),
          file.getPath(),
          file.getFname(),
          file.getMime(),
          file.getSize(),
          file.getExt(),
          file.getUrl(),
          (file.getProductReview() != null) ? file.getProductReview().getPrno() : null,
          (file.getProductDetail() != null) ? file.getProductDetail().getPdno() : null,
          (file.getNotice() != null) ? file.getNotice().getNno() : null,
          file.getType(),
          file.getRegDate(),
          file.getModDate()
        ))
        .toList();

      return ProductReviewDto
        .builder()
          .prno(productReview.getPrno())
          .pno(productReview.getProduct().getPno())
          .mno(productReview.getMember().getMno())
          .content(productReview.getContent())
          .rating(productReview.getRating())
          .count(productReview.getCount())
          .fileDtos(fileDtos)
        .build();
  }

}
