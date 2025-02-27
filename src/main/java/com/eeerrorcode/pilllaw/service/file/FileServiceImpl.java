package com.eeerrorcode.pilllaw.service.file;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.file.File;
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
      log.info("saveFile 호출됨 - 파일 DTO 값: {}", fileDto);
  
      if(fileDto.getType() == null) {
          throw new IllegalArgumentException("파일 타입 없음");
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
          .type(fileDto.getType()) // ✅ `type` 필드가 `null`인지 확인!
          .build();
  
      log.info("파일 엔티티 변환 완료: {}", file);
  
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
      List<File> fileList = fileRepository.findFilesByPno(pno);
      
      if (fileList == null || fileList.isEmpty()) {
          throw new RuntimeException("해당 상품의 이미지 파일이 존재하지 않습니다: " + pno);
      }
      
      return fileList.get(0).getUuid();
  }
  
  
}
