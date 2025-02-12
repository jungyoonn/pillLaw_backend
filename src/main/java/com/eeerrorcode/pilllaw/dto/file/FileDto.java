package com.eeerrorcode.pilllaw.dto.file;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.file.FileType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {

  private String uuid;
  private String origin;
  private String path;
  private String fname;
  private String mime;
  private Long size;
  private String ext;
  private String url;
  private Long prno;
  private Long pdno;
  private Long nno;
  private FileType type;
  private LocalDateTime regDate, modDate;

}
