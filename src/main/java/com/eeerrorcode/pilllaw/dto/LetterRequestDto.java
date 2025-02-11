package com.eeerrorcode.pilllaw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterRequestDto {
    private String sender;
    private String receiver;
    private String content;
}
