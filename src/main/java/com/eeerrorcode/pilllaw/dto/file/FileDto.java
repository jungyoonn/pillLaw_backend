package com.eeerrorcode.pilllaw.dto.file;

import java.time.LocalDateTime;
import java.util.UUID;

import com.eeerrorcode.pilllaw.entity.file.FileType;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

  public FileDto(String uuid, String origin, String path, String fname, String mime, Long size, String ext, String url, Long prno, Long pdno, Long nno, FileType type, LocalDateTime regDate, LocalDateTime modDate) {
    this.uuid = (uuid != null) ? uuid : UUID.randomUUID().toString();
    this.origin = origin;
    this.path = path;
    this.fname = fname;
    this.mime = mime;
    this.size = size;
    this.ext = ext;
    this.url = url;
    this.prno = prno;
    this.pdno = pdno;
    this.nno = nno;
    this.type = type;
    this.regDate = regDate;
    this.modDate = modDate;
  }

}
