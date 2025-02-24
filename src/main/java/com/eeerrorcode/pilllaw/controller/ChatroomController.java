package com.eeerrorcode.pilllaw.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.entity.chat.Chatroom;
import com.eeerrorcode.pilllaw.service.chat.ChatRoomService;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatroomController {

    private final ChatRoomService chatRoomService;

    // 생성자 주입
    public ChatroomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    // 특정 채팅방 ID로 채팅방 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<Chatroom>> getChatroomsByChatRoomId(@PathVariable long chatRoomId) {
        List<Chatroom> chatrooms = chatRoomService.findByChatrooms(chatRoomId);

        if (chatrooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(chatrooms);
    }

    // 모든 채팅방 목록 조회
    @GetMapping("/list/{chatRoomId}")
    public ResponseEntity<List<Chatroom>> getAllChatrooms() {
        List<Chatroom> chatrooms = chatRoomService.findByChatrooms(1); // 0은 모든 채팅방을 의미 (필요에 따라 수정)
        
        if (chatrooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(chatrooms);
    }
    @PostMapping("/postchat")
    public ResponseEntity<Chatroom> createChatroom(@RequestBody Chatroom chatroom) {
        // 서비스 레이어를 통해 채팅방 저장
        Chatroom savedChatroom = chatRoomService.saveChatroom(chatroom);

        // 채팅방 생성이 성공적으로 이루어졌다면, 201 상태 코드와 함께 생성된 채팅방 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChatroom);
    }
}
