package com.eeerrorcode.pilllaw.dto;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;

import lombok.Getter;

@Getter
public class LetterResponseDto {
    private Long id;
    private Member sender;
    private Member receiver;
    private String content;
    private LocalDateTime sentAt;

    public LetterResponseDto(Letter letter) {
        this.id = letter.getLetterId(); 
        this.sender = letter.getSender();
        this.receiver = letter.getReceiver();
        this.content = letter.getContent();
        this.sentAt = letter.getSentAt();



    }
    
}
