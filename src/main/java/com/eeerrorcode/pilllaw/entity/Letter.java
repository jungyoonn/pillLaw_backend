package com.eeerrorcode.pilllaw.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Builder
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
@Entity
@Data
@Table(name = "tbl_letter")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long letter;

    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime sentAt;
    
}
