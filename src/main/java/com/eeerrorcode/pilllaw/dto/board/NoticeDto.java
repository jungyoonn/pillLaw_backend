package com.eeerrorcode.pilllaw.dto.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eeerrorcode.pilllaw.dto.file.FileDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
public class NoticeDto {

  private Long nno;
  private Long mno;
  private String title;
  private String content;
  private Long count; 
  private LocalDateTime regDate, modDate;
  
  @Default
  private List<FileDto> fileDtos = new ArrayList<>();

  public NoticeDto(Long nno, Long mno, String title, String content, Long count, LocalDateTime regDate, LocalDateTime modDate, List<FileDto> fileDtos) {
    this.nno = nno;
    this.mno = mno;
    this.title = title;
    this.content = content;
    this.count = count; 
    this.regDate = regDate;
    this.modDate = modDate;
    this.fileDtos = (fileDtos != null) ? fileDtos : new ArrayList<>(); 
  }

}
