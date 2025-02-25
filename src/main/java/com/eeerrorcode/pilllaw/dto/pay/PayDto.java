package com.eeerrorcode.pilllaw.dto.pay;


import com.eeerrorcode.pilllaw.entity.pay.Pay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayDto {
    private Long no;
    private Long ono;
    private String method;
    private String status;
    private int totalPrice;
    private String impUid;

    public PayDto(Pay pay) {
        this.no = pay.getNo();
        this.ono = pay.getOrder().getOno();
        this.method = pay.getMethod().name();
        this.status = pay.getStatus().name();
        this.totalPrice = pay.getTotalPrice();
        this.impUid = pay.getImpUid();
    }
}
