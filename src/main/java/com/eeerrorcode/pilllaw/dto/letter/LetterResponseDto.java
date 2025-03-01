package com.eeerrorcode.pilllaw.dto.letter;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LetterResponseDto {
    private long letterId;
    private String nickName;
    private long senderId;  // Member 객체 포함
    private long receiverId; // Member 객체 포함
    private String content;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private boolean deletedBySender;
    private boolean deletedByReceiver;

    // public LetterResponseDto(Letter letter) {
    //     this.id = letter.getLetterId(); 
    //     this.sender = letter.getSenderId();
    //     this.receiver = letter.getReceiverId();
    //     this.content = letter.getContent();
    //     this.sentAt = letter.getSentAt();



    // }
    
}
