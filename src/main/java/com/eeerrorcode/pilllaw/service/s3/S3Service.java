package com.eeerrorcode.pilllaw.service.s3;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;

@Service
@Log4j2
public class S3Service {
  
  private final S3Client s3Client; // 🔹 외부에서 주입받도록 변경

  @Value("${aws.s3.bucket-name}")
  private String bucketName;

  @Value("${aws.s3.base-url}")
  private String baseUrl;

  // 🔹 S3Client를 GlobalConfig에서 주입받도록 변경
  public S3Service(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  @PostConstruct
  public void init() {
    log.info("✅ S3Service 초기화 완료");
    log.info("✅ 사용 중인 S3 Bucket: {}", bucketName);
    log.info("✅ Base URL: {}", baseUrl);
  }

  public String uploadFile(MultipartFile file, String key) {
    try {
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .contentType(file.getContentType())
        .build();
        
      log.info("📤 파일 업로드 시도 중: {}", key);
      s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

      return baseUrl + "/" + key;
    } catch (S3Exception | IOException e) {
      log.error("❌ 파일 업로드 실패: {}", e.getMessage());
      throw new RuntimeException("파일 업로드 실패!", e);
    }
  }

  public byte[] fileDownload(String key) {
    try {
      GetObjectRequest getObjectRequest = GetObjectRequest
      .builder()
        .bucket(bucketName)
        .key(key)
      .build();

      ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
      return response.readAllBytes();
    } catch (S3Exception | IOException e) {
      log.error("❌ 파일 다운로드 실패: {}", e.getMessage());
      throw new RuntimeException("파일 다운로드 실패: " + key, e);
    }
  }
}
