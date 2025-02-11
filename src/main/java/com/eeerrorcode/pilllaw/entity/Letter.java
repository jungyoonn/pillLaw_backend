package com.eeerrorcode.pilllaw.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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
