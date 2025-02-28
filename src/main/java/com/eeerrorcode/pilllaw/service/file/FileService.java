package com.eeerrorcode.pilllaw.service.file;

import java.util.List;
import java.util.Optional;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.file.File;

public interface FileService {
  FileDto saveFile(FileDto dto);
  Optional<FileDto> getFileByUuid(String uuid);
  String getFirstUUIDByPNO(Long pno);
  List<String> getImageListByPno(Long pno);
  List<String> getDetailListByPno(Long pno);

  default FileDto toDto(File file){
    return FileDto
    .builder()
      .uuid(file.getUuid())
      .origin(file.getOrigin())
      .path(file.getPath())
      .fname(file.getFname())
      .mime(file.getMime())
      .size(file.getSize())
      .ext(file.getExt())
      .url(file.getUrl())
      .prno(file.getProductReview().getPrno())
      .pdno(file.getProductDetail().getPdno())
      .nno(file.getNotice().getNno())
      .type(file.getType())
    .build();
  }
}
