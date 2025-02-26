package com.eeerrorcode.pilllaw.dto.pay;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.pay.Point.PointStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class PointDto {
  private Long pono;
  private Long mno; 
  private long point;
  private PointStatus status;
  private LocalDateTime endDate;
  private LocalDateTime regdate;
  private LocalDateTime moddate;
}
