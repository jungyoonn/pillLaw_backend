package com.eeerrorcode.pilllaw.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.pay.PayDto;
import com.eeerrorcode.pilllaw.dto.pay.PayRequestDto;
import com.eeerrorcode.pilllaw.entity.pay.Pay;
import com.eeerrorcode.pilllaw.service.order.OrderService;
import com.eeerrorcode.pilllaw.service.pay.IamportService;
import com.eeerrorcode.pilllaw.service.pay.PayService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pay")
@Log4j2
public class PayController {

  private final PayService payService;
  private final IamportService iamportService;
  private final OrderService orderService;

  /**
   * 결제 요청 (Pay 엔티티 생성)
   */
  @PostMapping("/req")
  public ResponseEntity<?> requestPayment(@RequestBody PayRequestDto requestDto) {
    try {
      // 결제 방법 검증
      Pay.PaymentMethod paymentMethod;
      try {
          paymentMethod = Pay.PaymentMethod.valueOf(requestDto.getMethod());
      } catch (IllegalArgumentException e) {
          return ResponseEntity.badRequest().body("잘못된 결제 방법입니다. (지원: 카드)");
      }

      Pay pay = payService.requestPayment(requestDto.getOno(), paymentMethod, requestDto.getTotalPrice(),  requestDto.getImpUid());
      return ResponseEntity.ok(new PayDto(pay));

    } catch (Exception e) {
      log.error("결제 요청 실패", e);
      return ResponseEntity.internalServerError().body("서버 오류: 결제 요청 실패");
    }
  }

  /**
   * 결제 완료 처리 (Iamport 결제 검증 후 Pay 엔티티 업데이트)
   */
  @PostMapping("/complete")
  public ResponseEntity<?> completePayment(@RequestBody Map<String, Object> requestData) {
    Long ono = requestData.containsKey("ono") ? ((Number) requestData.get("ono")).longValue() : null;
    String imp_uid = (String) requestData.get("imp_uid");

    try {
      // IAMPORT API를 통해 결제 검증
      Map<String, Object> paymentInfo = iamportService.validatePayment(imp_uid);
      if (paymentInfo == null) {
        return ResponseEntity.badRequest().body("결제 검증 실패: (IAMPORT API) 결제 정보를 찾을 수 없습니다.");
      }
      // 결제된 금액 확인
      int paidAmount = ((Number) paymentInfo.get("amount")).intValue(); // 아임포트에서 받은 결제 금액
      Pay pay = payService.getPaymentByOrder(ono); // DB에 저장된 결제 정보
      if (pay == null) {
        return ResponseEntity.badRequest().body("결제 검증 실패: 해당 주문의 결제 정보가 없습니다.");
      }
      // 이미 결제 완료된 경우 중복 업데이트 방지
      if (pay.getStatus() == Pay.PaymentStatus.SUCCESS) {
        return ResponseEntity.ok("이미 완료된 결제입니다.");
      }
      if (pay.getTotalPrice() != paidAmount) {
        return ResponseEntity.badRequest().body("결제 금액 불일치 (DB: " + pay.getTotalPrice() + "원, IAMPORT: " + paidAmount + "원)");
      }
      // 결제 성공 처리 (DB 업데이트)
      Pay updatedPay = payService.successPayment(pay.getNo());
      
      // 배송상태 추가
      // orderService.addDelivery(ono);

      return ResponseEntity.ok(new PayDto(updatedPay));

    } catch (Exception e) {
      log.error("결제 완료 처리 중 오류 발생", e);
      return ResponseEntity.internalServerError().body("서버 오류: 결제 완료 처리 실패");
    }
  }
  // @GetMapping
  // public ResponseEntity<?> Paylist() {
  //   List<AdminPayDto> pay = payService.findList();
  //     return ResponseEntity.ok(pay);
  // }
  
}
