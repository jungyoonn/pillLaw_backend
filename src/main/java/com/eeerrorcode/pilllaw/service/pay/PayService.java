package com.eeerrorcode.pilllaw.service.pay;

import java.util.List;

import com.eeerrorcode.pilllaw.entity.pay.Pay;


public interface PayService {
  Pay requestPayment(Long orderNo, Pay.PaymentMethod method, int totalPrice, String impUid);

  boolean verifyPayment(Long payNo, int verifiedAmount);

  Pay successPayment(Long payNo);

  Pay failPayment(Long payNo);

  Pay getPaymentByOrder(Long orderNo);
  
  // List<AdminPayDto> findList();
}
