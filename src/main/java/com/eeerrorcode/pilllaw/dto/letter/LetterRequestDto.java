package com.eeerrorcode.pilllaw.dto.letter;

import java.util.ArrayList;
import java.util.HashSet;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.follow.Letter;
import com.eeerrorcode.pilllaw.entity.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterRequestDto {
    private long letterId;
    private long senderId;
    private long receiverId;
    private String content;

   
    // public LetterRequestDto(long senderId, long receiverId, String content) {
    //     this.senderId = senderId;
    //     this.receiverId = receiverId;
    //     this.content = content;
    // }

    // public long getSenderId() { return senderId; }
    // public long getReceiverId() { return receiverId; }
    // public String getContent() { return content; }
}
