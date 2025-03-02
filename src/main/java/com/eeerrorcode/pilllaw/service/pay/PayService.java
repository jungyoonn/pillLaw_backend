package com.eeerrorcode.pilllaw.service.pay;


import com.eeerrorcode.pilllaw.entity.pay.Pay;


public interface PayService {
  public Pay requestPayment(Long ono, Pay.PaymentMethod method, int totalPrice, String impUid);

  boolean verifyPayment(Long payNo, int verifiedAmount);

  Pay successPayment(Long payNo);

  Pay failPayment(Long payNo);

  Pay getPaymentByOrder(Long ono);

  Pay cancelPayment(Long payNo);
  
  // List<AdminPayDto> findList();
}
