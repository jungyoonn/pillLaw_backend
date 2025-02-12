package com.eeerrorcode.pilllaw.dto.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eeerrorcode.pilllaw.dto.file.FileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {

  private Long nno;
  private Long mno;
  private String title;
  private String content;
  private Long count; 
  private LocalDateTime regDate, modDate;
  
  @Default
  private List<FileDto> fileDtos = new ArrayList<>();

}
