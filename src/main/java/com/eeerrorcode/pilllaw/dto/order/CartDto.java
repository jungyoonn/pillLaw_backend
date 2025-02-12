package com.eeerrorcode.pilllaw.dto.order;

import java.time.LocalDateTime;
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
public class CartDto {
  private Long cno;
  private Long mno;
  private LocalDateTime regdate, moddate;
}