package com.eeerrorcode.pilllaw.entity.pay;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.member.Member;

@Entity(name = "tbl_point")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    private long point;

    @Enumerated(EnumType.STRING)
    private PointStatus status;
    private LocalDateTime endDate;

    public enum PointStatus {
        EARNED, USED, EXPIRED
    }
}
