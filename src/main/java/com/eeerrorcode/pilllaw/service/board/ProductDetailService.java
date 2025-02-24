package com.eeerrorcode.pilllaw.service.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.dto.product.ProductDetailDto;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.file.FileType;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;

public interface ProductDetailService {

  void registerDetail(ProductDetailDto dto);
  
  ProductDetailDto showDetailsByPno(Long pno);
  
  void modifyDetail(ProductDetailDto dto);
  
  void deleteDetail(Long pno);

  Page<ProductDetailDto> showMyList(Pageable pageable, Long mno);

  Page<ProductDetailDto> showAll(Pageable pageable);


  default ProductDetail toEntity(ProductDetailDto dto){

    ProductDetail productDetail = ProductDetail
    .builder()
      .pdno(dto.getPdno())
      .content(dto.getContent())
      .count(dto.getCount())
      .member(Member.builder().mno(dto.getMno()).build())
      .product(Product.builder().pno(dto.getPno()).build())
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
          .productDetail(productDetail)
          .type(FileType.REVIEW)
        .build();
        productDetail.getFiles().add(file);
      });
    }

    return productDetail;
  }

  default ProductDetailDto toDto(ProductDetail detail){
    List<FileDto> fileDtos = detail.getFiles().stream()
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

    ProductDetailDto productDetailDto = ProductDetailDto
    .builder()
      .pdno(detail.getPdno())
      .content(detail.getContent())
      .count(detail.getCount())
      .mno(detail.getMember().getMno())
      .pno(detail.getProduct().getPno())
      .regDate(detail.getRegDate())
      .modDate(detail.getModDate())
      .fileDtos(fileDtos)
    .build();

    return productDetailDto;

  }

}
