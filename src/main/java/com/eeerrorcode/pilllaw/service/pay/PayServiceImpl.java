package com.eeerrorcode.pilllaw.service.pay;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.entity.pay.Delivery;
import com.eeerrorcode.pilllaw.entity.pay.Pay;
import com.eeerrorcode.pilllaw.repository.order.OrderRepository;
import com.eeerrorcode.pilllaw.repository.pay.DeliveryRepository;
import com.eeerrorcode.pilllaw.repository.pay.PayRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class PayServiceImpl implements PayService {

        private final PayRepository payRepository;
        private final OrderRepository orderRepository;
        private final IamportService iamportService;
        private final DeliveryRepository deliveryRepository;

        // 결제 요청
        @Transactional
        public Pay requestPayment(Long ono, Pay.PaymentMethod method, int totalPrice, String impUid) {
                // Order가 정상적으로 조회되는지 확인
                Order order = orderRepository.findById(ono)
                                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
                log.info("Order found: {}", order); // Order 정보 로그 출력

                Pay pay = Pay.builder()
                                .order(order)
                                .method(method)
                                .status(Pay.PaymentStatus.PENDING) // 기본값 PENDING
                                .totalPrice(totalPrice)
                                .impUid(impUid)
                                .build();

                log.info("Payment object created: {}", pay); // 생성된 Pay 객체 로그 출력

                Pay savedPay = payRepository.save(pay);
                log.info("Payment saved: {}", savedPay); // 저장된 Pay 객체 로그 출력

                return savedPay;
        }

        // 결제 검증
        public boolean verifyPayment(Long payNo, int verifiedAmount) {
                Pay pay = payRepository.findById(payNo)
                                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

                return pay.getTotalPrice() == verifiedAmount; // 검증 성공 여부 반환
        }

        // 결제 성공 처리
        @Transactional
        public Pay successPayment(Long payNo) {
                Pay pay = payRepository.findById(payNo)
                                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

                pay = Pay.builder()
                                .no(pay.getNo())
                                .order(pay.getOrder())
                                .method(pay.getMethod())
                                .status(Pay.PaymentStatus.SUCCESS) // 결제 상태 업데이트
                                .totalPrice(pay.getTotalPrice())
                                .impUid(pay.getImpUid())
                                .build();
                return payRepository.save(pay);

        }

        // 결제 실패 처리
        @Transactional
        public Pay failPayment(Long payNo) {
                Pay pay = payRepository.findById(payNo)
                                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

                pay = Pay.builder()
                                .no(pay.getNo())
                                .order(pay.getOrder())
                                .method(pay.getMethod())
                                .status(Pay.PaymentStatus.FAIL) // 결제 실패 처리
                                .totalPrice(pay.getTotalPrice())
                                .impUid(pay.getImpUid())
                                .build();

                return payRepository.save(pay);
        }

        // ono를 통해 결제 조회
        public Pay getPaymentByOrder(Long ono) {
                return payRepository.findByOrderOno(ono)
                                .orElseThrow(() -> new IllegalArgumentException("해당 주문에 대한 결제 정보가 없습니다."));
        }


        //결제 취소
        @Transactional
        @Override
        public Pay cancelPayment(Long payNo) {
            Pay pay = payRepository.findById(payNo).orElseThrow(() ->
                    new IllegalArgumentException("해당 결제 정보를 찾을 수 없습니다."));
        
            if (pay.getStatus() == Pay.PaymentStatus.REFUND) {
                throw new IllegalStateException("이미 환불된 결제입니다.");
            }
        
            // IAMPORT API를 통해 환불 요청
            Map<String, Object> cancelResponse = iamportService.cancelPayment(pay.getImpUid(), "결제 취소");
        
            if (cancelResponse == null) {
                throw new RuntimeException("환불 처리 실패: IAMPORT 응답이 없습니다.");
            }
        
            // 결제 상태를 REFUND로 변경
            pay.setStatus(Pay.PaymentStatus.REFUND);
            payRepository.save(pay);
        
            // 배송 상태를 CANCELLED로 변경
            deliveryRepository.findByOrderOno(pay.getOrder().getOno())
                    .ifPresent(delivery -> {
                        delivery.setDeliveryStatus(Delivery.DeliveryStatus.CANCELLED);
                        deliveryRepository.save(delivery);
                    });
        
            log.info("결제 환불 완료 - PayNo: {}, ImpUid: {}", payNo, pay.getImpUid());
            return pay;
        }
}
