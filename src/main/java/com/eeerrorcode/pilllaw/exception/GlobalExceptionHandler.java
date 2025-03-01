package com.eeerrorcode.pilllaw.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 중복된 장바구니 상품 예외 처리
    @ExceptionHandler(DuplicateCartItemException.class)
    public ResponseEntity<String> handleDuplicateCartItemException(DuplicateCartItemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // 기타 예외 처리 (예외 메시지를 숨기고, "서버 오류"만 노출)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
}