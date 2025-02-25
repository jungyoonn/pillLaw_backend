package com.eeerrorcode.pilllaw.dto.pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRequestDto {
    private Long ono;
    private String method;
    private int totalPrice;
    private String impUid;
}
