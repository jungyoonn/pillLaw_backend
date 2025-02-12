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
public class OrderDto {
  private Long ono;
  private Long mno;
  private String name;
  private String tel;
  private String request;
  private long totalAmount;
  private long usingPoint;
  private LocalDateTime regdate, moddate;
}
  
