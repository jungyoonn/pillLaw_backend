package com.eeerrorcode.pilllaw.dto;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
