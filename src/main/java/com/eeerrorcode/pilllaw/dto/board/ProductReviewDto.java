package com.eeerrorcode.pilllaw.dto.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.eeerrorcode.pilllaw.dto.file.FileDto;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductReviewDto {
  
  private Long prno;

  private Long pno;

  private Long mno;

  private String nickName;

  // @JsonRawValue
  // @JsonDeserialize(using = JsonDeserializer.None.class)
  private String content;

  private Integer rating;

  private Long count;

  private List<String> imgUrls;

  @Default
  private List<FileDto> fileDtos = new ArrayList<>();

  private LocalDateTime regDate, modDate;

  public List<String> getImageUrls() {
    if (fileDtos == null || fileDtos.isEmpty()) {
        return Collections.emptyList();
    }
    return fileDtos.stream()
        .map(FileDto::getUrl)  // ✅ 파일 URL 리스트 반환
        .collect(Collectors.toList());
  }


  public ProductReviewDto(Long prno, Long pno, Long mno, String content, Integer rating, Long count, LocalDateTime regDate, LocalDateTime modDate, List<FileDto> fileDtos) {
    this.prno = prno;
    this.pno = pno;
    this.mno = mno;
    this.content = content;
    this.rating = (rating != null && rating >= 1 && rating <= 5) ? rating : 1; 
    this.count = (count != null) ? count : 0;
    this.regDate = regDate;
    this.modDate = modDate;
    this.fileDtos = (fileDtos != null) ? fileDtos : new ArrayList<>(); 
  }


    
}
