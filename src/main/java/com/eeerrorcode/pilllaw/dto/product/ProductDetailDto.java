package com.eeerrorcode.pilllaw.dto.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.eeerrorcode.pilllaw.dto.file.FileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
  
  private Long pdno;

  private Long pno;

  private Long mno;

  private String content;

  private Long count;

  private String detailUrl;

  @Default
  private List<FileDto> fileDtos = new ArrayList<>();

  private LocalDateTime regDate, modDate;
  
  public List<String> getImageUrls() {
    if (fileDtos == null || fileDtos.isEmpty()) {
        return Collections.emptyList();
    }
    return fileDtos.stream()
        .map(FileDto::getUrl)
        .collect(Collectors.toList());
  }
}
