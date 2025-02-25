package com.eeerrorcode.pilllaw.service.pay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.*;

@Service
@Log4j2
public class IamportServiceImpl implements IamportService{
  @Value("${iamport.api-key}")
  private String apiKey;

  @Value("${iamport.api-secret}")
  private String apiSecret;
  
  RestTemplate restTemplate = new RestTemplate();
  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String getAccessToken() {
    String url = "https://api.iamport.kr/users/getToken";
    String requestBody = null;

    try {
      Map<String, String> body = new HashMap<>();
      body.put("imp_key", apiKey);
      body.put("imp_secret", apiSecret);
      requestBody = objectMapper.writeValueAsString(body); // JSON 변환
    } catch (JsonProcessingException e) {
      log.error("JSON 변환 실패", e);
      throw new RuntimeException("JSON 변환 실패");
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    
    // log.info("IAMPORT API Key: {}", apiKey);
    // log.info("IAMPORT API Secret: {}", apiSecret != null ? "*****" : "NULL");
    // log.info("IAMPORT 요청 JSON: {}", requestBody);

    ResponseEntity<Map> response;
    try {
      response = restTemplate.postForEntity(url, entity, Map.class);
      // log.info("IAMPORT 응답: {}", response.getBody());
    } catch (Exception e) {
      log.error("IAMPORT 토큰 요청 실패", e);
      throw new RuntimeException("IAMPORT 토큰 요청 실패", e);
    }

    if (response.getBody() == null || response.getBody().get("response") == null) {
      throw new RuntimeException("IAMPORT 토큰 응답이 null입니다.");
    }

    return (String) ((Map) response.getBody().get("response")).get("access_token");
  }


  @Override
  public Map<String, Object> validatePayment(String impUid) {
    // log.info("IAMPORT Access Token 요청");
    String token = getAccessToken();  // IAMPORT 토큰 가져오기
    // log.info("IAMPORT Access Token: {}", token);
    
    if (token == null || token.isEmpty()) {
        throw new RuntimeException("IAMPORT Access Token이 null 또는 비어 있습니다.");
    }

    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.iamport.kr/payments/" + impUid;

    // log.info("IAMPORT 결제 검증 요청 impUid: {}", impUid);

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    
    try {
      ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
      // log.info("IAMPORT 결제 검증 응답: {}", response.getBody());
      if (response.getBody() == null || response.getBody().get("response") == null) {
          throw new RuntimeException("IAMPORT 결제 검증 응답이 null입니다.");
      }

      return (Map<String, Object>) response.getBody().get("response");

    } catch (Exception e) {
      log.error("IAMPORT 결제 검증 실패", e);
      throw new RuntimeException("IAMPORT 결제 검증 실패", e);
    }
  }

      /**
     * 결제 환불 처리 (Iamport API)
     */
    @Override
    public Map<String, Object> cancelPayment(String impUid, String reason) {
        log.info("IAMPORT 결제 환불 요청 - impUid: {}, reason: {}", impUid, reason);
    
        // 1. impUid 유효성 검증
        if (impUid == null || impUid.trim().isEmpty()) {
            log.error("imp_uid가 null 또는 비어 있습니다.");
            throw new IllegalArgumentException("유효한 imp_uid를 입력해야 합니다.");
        }
    
        String token = getAccessToken();
        if (token == null || token.isEmpty()) {
            log.error("IAMPORT Access Token이 null 또는 비어 있습니다.");
            throw new RuntimeException("IAMPORT Access Token이 null 또는 비어 있습니다.");
        }
    
        String url = "https://api.iamport.kr/payments/cancel";
    
        // 2. 환불 요청 바디 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("imp_uid", impUid);  // 결제 고유번호
        requestBody.put("reason", reason);   // 환불 사유
    
        // 3. ObjectMapper로 JSON 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            throw new RuntimeException("환불 요청 바디 생성 실패", e);
        }
    
        // 4. 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
    
        try {
            // 5. Iamport API로 환불 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
    
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("IAMPORT 환불 요청 실패: HTTP {}", response.getStatusCode());
                throw new RuntimeException("IAMPORT 환불 요청 실패: " + response.getStatusCode());
            }
    
            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null || responseBody.get("response") == null) {
                log.error("IAMPORT 환불 응답이 null입니다.");
                throw new RuntimeException("IAMPORT 환불 응답이 null입니다.");
            }
    
            log.info("IAMPORT 환불 성공: {}", responseBody);
            return (Map<String, Object>) responseBody.get("response");
    
        } catch (HttpClientErrorException e) {
            log.error("IAMPORT 환불 요청 실패: {}", e.getResponseBodyAsString());
            throw new RuntimeException("IAMPORT 환불 요청 실패", e);
        } catch (Exception e) {
            log.error("IAMPORT 환불 요청 중 예외 발생", e);
            throw new RuntimeException("IAMPORT 환불 요청 실패", e);
        }
    }
    
}
