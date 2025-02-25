package com.eeerrorcode.pilllaw.service.file;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.eeerrorcode.pilllaw.entity.file.File;
import com.eeerrorcode.pilllaw.repository.file.FileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService{

  private final FileRepository fileRepository;

  @Override
  public FileDto saveFile(FileDto fileDto) {
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
      .type(fileDto.getType())
    .build();

    file = fileRepository.save(file);
    return new FileDto(file);
  }
  
}
