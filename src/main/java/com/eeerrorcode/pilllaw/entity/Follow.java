package com.eeerrorcode.pilllaw.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_follow")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Data
@Builder
public class Follow extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "sender_follow_id", nullable = false)
    private Member sender;
    // @ManyToOne
    // private String senderFollowId; //보낸 사람
    private String receiverFollowId; //받는 사람
    private LocalDate createdAt;
    private Boolean isFollowBack;

}
