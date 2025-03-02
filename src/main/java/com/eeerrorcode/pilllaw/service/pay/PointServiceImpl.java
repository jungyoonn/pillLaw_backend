package com.eeerrorcode.pilllaw.service.pay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.pay.PointDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.pay.Pay;
import com.eeerrorcode.pilllaw.entity.pay.Point;
import com.eeerrorcode.pilllaw.entity.pay.Point.PointStatus;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.pay.PayRepository;
import com.eeerrorcode.pilllaw.repository.pay.PointRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PayRepository payRepository;

    @Override
    @Transactional
    public PointDto addPoints(Long mno, long pointAmount) {
        // mno를 통해 Member 조회
        Member member = memberRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 포인트 적립 내역 추가
        Point point = Point.builder()
                .member(member)
                .point(pointAmount) // 적립된 포인트
                .status(PointStatus.EARNED) // 적립 상태
                .endDate(LocalDateTime.now().plusYears(1)) // 예시로 1년 뒤 만기
                .build();

        pointRepository.save(point);

        return toDto(point); // PointDto로 반환
    }

    @Scheduled(cron = "0 30 0 * * ?") // 매일 오전 00시 30분에 실행
    @Transactional
    public int addPointsForCompletedPayments() {
        log.info("포인트 적립 스케줄 작업이 시작되었습니다."); // 스케줄 실행 시작 로그 추가
        List<Pay> successfulPayments = payRepository.findByStatus(Pay.PaymentStatus.SUCCESS);
        int pointCount = 0;

        for (Pay payment : successfulPayments) {
            LocalDateTime paymentDate = payment.getRegDate();

            // 결제일에 2일을 더한 후, 자정(00시)에 30분을 더한 시간 계산
            LocalDateTime oneDayLater = paymentDate.plusDays(2).toLocalDate().atStartOfDay().plusMinutes(30);

            // 포인트 적립 조건: 현재 시간이 'oneDayLater'보다 크거나 같으면 포인트 적립
            if (LocalDateTime.now().isAfter(oneDayLater)) {
                Member member = payment.getOrder().getMember();
                double pointRatio = 0.02;
                long pointsToAdd = (long) (payment.getTotalPrice() * pointRatio);

                Point point = Point.builder()
                        .member(member)
                        .point(pointsToAdd)
                        .status(PointStatus.EARNED)
                        .endDate(LocalDateTime.now().plusYears(1))
                        .build();

                pointRepository.save(point);

                log.info("회원 mno: {}, 결제일: {}, oneDayLater: {}, 현재시간: {}", member.getMno(), paymentDate, oneDayLater,
                        LocalDateTime.now());

                pointCount++;
            }
        }
        return pointCount; // 적립된 포인트 개수 반환
    }

    @Override
    @Transactional
    public PointDto usePoints(Long mno, long pointAmount) {
        // mno를 통해 Member 조회
        Member member = memberRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 포인트 사용 내역 추가
        Point point = Point.builder()
                .member(member)
                .point(-pointAmount) // 사용된 포인트는 음수
                .status(PointStatus.USED) // 사용 상태
                .endDate(LocalDateTime.now().plusYears(1)) // 예시로 1년 뒤 만기
                .build();

        pointRepository.save(point);

        return toDto(point); // PointDto로 반환
    }

    @Override
    public long getTotalPoints(Long mno) {
        List<Point> points = pointRepository.findByMemberMno(mno);

        return points.stream()
                .mapToLong(Point::getPoint)
                .sum();
    }

    @Override
    public List<PointDto> getPointHistory(Long mno) {
        List<Point> points = pointRepository.findByMemberMno(mno);
        return points.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public int deleteExpiredPoints() {
        LocalDateTime now = LocalDateTime.now();
        List<Point> expiredPoints = pointRepository.findByEndDateBefore(now);
        int deletedCount = expiredPoints.size();
        pointRepository.deleteAll(expiredPoints);
        return deletedCount;
    }
}