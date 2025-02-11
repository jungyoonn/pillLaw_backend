package com.eeerrorcode.pilllaw.dto;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.Letter;

import lombok.Getter;

@Getter
public class LetterResponseDto {
    private Long id;
    private String sender;
    private String receiver;
    private String contet;
    private LocalDateTime sentAt;

    public LetterResponseDto(Letter letter) {
        this.id = letter.getLetter();
        this.sender = letter.getSender();
        this.receiver = letter.getReceiver();
        this.contet = letter.getContent();
        this.sentAt = letter.getSentAt();



    }
    
}
