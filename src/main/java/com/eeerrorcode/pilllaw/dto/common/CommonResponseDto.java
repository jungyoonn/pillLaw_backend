package com.eeerrorcode.pilllaw.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponseDto {
  private String msg;
  private Boolean ok;
  private Integer statusCode;
}
