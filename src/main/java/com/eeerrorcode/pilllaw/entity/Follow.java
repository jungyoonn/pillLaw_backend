package com.eeerrorcode.pilllaw.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Getter
// @Setter
// @Builder
@Data
@Entity
@Table(name = "tbl_follow")
public class Follow extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;
    private String senderFollowId; //보낸 사람
    private String receiverFollowId; //받는 사람
    private LocalDate createdAt;
    private Boolean isFollowBack;

}
