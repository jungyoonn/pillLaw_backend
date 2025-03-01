package com.eeerrorcode.pilllaw.service.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.board.Notice;
import com.eeerrorcode.pilllaw.entity.board.ProductReview;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.entity.product.Product;
import com.eeerrorcode.pilllaw.entity.product.ProductDetail;
import com.eeerrorcode.pilllaw.repository.file.FileRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class FileServiceImpl implements FileService{

  private final FileRepository fileRepository;

@Override
@Transactional
public FileDto saveFile(FileDto fileDto) {
    log.info("📌 saveFile 호출됨 - 파일 DTO 값: {}", fileDto);

    if (fileDto.getType() == null) {
        throw new IllegalArgumentException("파일 타입 없음");
    }

    if (fileDto.getPrno() == null && fileDto.getPdno() == null && fileDto.getNno() == null) {
        log.warn("⚠️ 연결된 ID가 없습니다! (prno, pdno, noticeId 중 하나가 필요)");
    }

    File file = File.builder()
        .uuid(fileDto.getUuid())
        .origin(fileDto.getOrigin())
        .path(fileDto.getPath())
        .fname(fileDto.getFname())
        .mime(fileDto.getMime())
        .size(fileDto.getSize())
        .ext(fileDto.getExt())
        .url(fileDto.getUrl())
        .type(fileDto.getType())
        .productReview(
            fileDto.getPrno() != null ? ProductReview.builder().prno(fileDto.getPrno()).build() : null
        )
        .productDetail(
            fileDto.getPdno() != null ? ProductDetail.builder().pdno(fileDto.getPdno()).build() : null
        )
        .notice(
            fileDto.getNno() != null ? Notice.builder().nno(fileDto.getNno()).build() : null
        )
        .build();

    log.info("📌 파일 엔티티 변환 완료: {}", file);

    file = fileRepository.save(file);
    log.info("파일 저장 완료: {}", file);

    return new FileDto(file);
}

  

  @Override
  public Optional<FileDto> getFileByUuid(String uuid) {
    return fileRepository.findById(uuid).map(FileDto::new);
  }


  @Override
  public String getFirstUUIDByPNO(Long pno) {
    List<String> fileList = fileRepository.findFilesByPno(pno);
    log.info("파일 리스트 개수: {}", fileList.size());  
    if (fileList.isEmpty()) {
        log.warn("대표 이미지가 없습니다. PNO: {}", pno);
        return null;
    }
    return fileList.get(0);
  }
  


@Override
public List<String> getDetailListByPno(Long pno) {
    return null;
}


@Override
public List<String> getImageListByPno(Long pno) {
    List<String> StringList = fileRepository.findFilesByPno(pno); 
    return StringList;
}

@Override
public List<FileDto> getFilesByReviewId(Long prno) {
    List<File> files = fileRepository.findByProductReview(ProductReview.builder().prno(prno).build());
    log.info("📌 리뷰 ID: {} → 조회된 파일 개수: {}", prno, files.size());  // ✅ 로그 추가
    return files.stream().map(FileDto::new).collect(Collectors.toList());
}


  
 
  
  
}
