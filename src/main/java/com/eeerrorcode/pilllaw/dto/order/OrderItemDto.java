package com.eeerrorcode.pilllaw.dto.order;

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
public class OrderItemDto {
  private Long oino;
  private Long ono;
  private Long pno;
  private Long price;
  private long subday;
  private long quantity;
}