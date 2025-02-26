package com.eeerrorcode.pilllaw.service.pay;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.entity.order.Order;
import com.eeerrorcode.pilllaw.entity.pay.Pay;
import com.eeerrorcode.pilllaw.repository.order.OrderRepository;
import com.eeerrorcode.pilllaw.repository.pay.PayRepository;
import com.eeerrorcode.pilllaw.service.order.OrderItemService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService; // OrderItemService 추가

    /**
     * 1️⃣ 결제 요청
     */
    @Transactional
    public Pay requestPayment(Long ono, Pay.PaymentMethod method, int totalPrice, String impUid) {
        // Order가 정상적으로 조회되는지 확인
        Order order = orderRepository.findById(ono)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        log.info("Order found: {}", order); // Order 정보 로그 출력

        Pay pay = Pay.builder()
                .order(order)
                .method(method)
                .status(Pay.PaymentStatus.실패) // 기본값은 실패로 설정
                .totalPrice(totalPrice)
                .impUid(impUid)
                .build();

        log.info("Payment object created: {}", pay); // 생성된 Pay 객체 로그 출력

        Pay savedPay = payRepository.save(pay);
        log.info("Payment saved: {}", savedPay); // 저장된 Pay 객체 로그 출력

        return savedPay;
    }

    /**
     * 2️⃣ 결제 검증 (DB 내 결제 정보와 실제 결제 정보 비교)
     */
    public boolean verifyPayment(Long payNo, int verifiedAmount) {
        Pay pay = payRepository.findById(payNo)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        return pay.getTotalPrice() == verifiedAmount; // 검증 성공 여부 반환
    }

    /**
     * 3️⃣ 결제 성공 처리
     */
    @Transactional
    public Pay successPayment(Long payNo) {
        Pay pay = payRepository.findById(payNo)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        pay = Pay.builder()
                .no(pay.getNo())
                .order(pay.getOrder())
                .method(pay.getMethod())
                .status(Pay.PaymentStatus.완료) // ✅ 결제 상태 업데이트
                .totalPrice(pay.getTotalPrice())
                .impUid(pay.getImpUid())
                .build();

        // 주문 번호(ono)와 회원 번호(mno) 가져오기
        Long ono = pay.getOrder().getOno(); // 주문 번호
        Long mno = pay.getOrder().getMember().getMno(); // 회원 번호

        // 결제 성공 후, 장바구니 항목을 주문 항목으로 변환
        orderItemService.addOrderItems(mno, ono);

        // 결제 정보 저장
        Pay savedPay = payRepository.save(pay);
        log.info("Payment saved: {}", savedPay);

        return savedPay;
    }

    /**
     * 4️⃣ 결제 실패 처리
     */
    @Transactional
    public Pay failPayment(Long payNo) {
        Pay pay = payRepository.findById(payNo)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        pay = Pay.builder()
                .no(pay.getNo())
                .order(pay.getOrder())
                .method(pay.getMethod())
                .status(Pay.PaymentStatus.실패) // ✅ 결제 실패 처리
                .totalPrice(pay.getTotalPrice())
                .impUid(pay.getImpUid())
                .build();

        return payRepository.save(pay);
    }

    /**
     * 주문 번호(ono)를 기준으로 결제 정보 조회
     */
    public Pay getPaymentByOrder(Long ono) {
        return payRepository.findByOrderOno(ono)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문에 대한 결제 정보가 없습니다."));
    }

    // @Override
    // public List<AdminPayDto> findList() {
    // List<AdminPayDto> dto = payRepository.findAll(Sort.by(Sort.Direction.DESC,
    // "no"))
    // .stream()
    // .map(AdminPayDto::new)
    // .collect(Collectors.toList());
    // // dto.forEach(System.out::println);
    // return dto;
    // }

}
