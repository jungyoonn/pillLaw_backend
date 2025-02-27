package com.eeerrorcode.pilllaw.service.file;

import java.util.Optional;

import com.eeerrorcode.pilllaw.dto.file.FileDto;

public interface FileService {
  FileDto saveFile(FileDto dto);
  Optional<FileDto> getFileByUuid(String uuid);
  String getFirstUUIDByPNO(Long pno);
}
