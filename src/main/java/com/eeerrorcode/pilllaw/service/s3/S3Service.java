package com.eeerrorcode.pilllaw.service.s3;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
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

  @Value("{${aws.s3.folder-structure.detail}}")
  private String productDetailTemplate;

  @Value("${aws.s3.folder-structure.product}")
  private String productPathTemplate;

  @Value("${aws.s3.folder-structure.review}")
  private String reviewPathTemplate;

  @Value("${aws.s3.region}")
  private String region;

  public String generateProductImageUrl(Long pno, String fileName) {
    String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    String path = productPathTemplate.replace("{year}", year).replace("{pno}", String.valueOf(pno));
    if (!path.endsWith("/")) {
        path += "/";
    }
    
    // 실제 S3 키 확인을 위한 로깅
    String s3Key = path + fileName;
    String fullUrl = baseUrl + bucketName + "/" + s3Key;
    
    log.info("생성된 S3 키: {}", s3Key);
    log.info("생성된 전체 URL: {}", fullUrl);
    
    // S3에 해당 키가 존재하는지 확인 (옵션)
    try {
        s3Client.headObject(builder -> builder.bucket(bucketName).key(s3Key).build());
        log.info("S3에 파일 존재: {}", s3Key);
    } catch (Exception e) {
        log.warn("S3에 파일이 존재하지 않음: {}", s3Key);
    }
    
    return fullUrl;
  }

  public String generateDetailImageUrl(Long pno, String fileName) {
    String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    String path = productDetailTemplate.replace("{year}", year).replace("{pno}", String.valueOf(pno));
    if (!path.endsWith("/")) {
        path += "/";
    }
    
    // 실제 S3 키 확인을 위한 로깅
    String s3Key = path + fileName;
    String fullUrl = baseUrl + bucketName + "/" + s3Key;
    
    log.info("생성된 S3 키: {}", s3Key);
    log.info("생성된 전체 URL: {}", fullUrl);
    
    // S3에 해당 키가 존재하는지 확인 (옵션)
    try {
        s3Client.headObject(builder -> builder.bucket(bucketName).key(s3Key).build());
        log.info("S3에 파일 존재: {}", s3Key);
    } catch (Exception e) {
        log.warn("S3에 파일이 존재하지 않음: {}", s3Key);
    }
    
    return fullUrl;
  }


  public String getProductMainImage(Long pno) {
    String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    String prefix = "uploads/" + year + "/product/" + pno + "/img/";

    ListObjectsRequest listObjects = ListObjectsRequest.builder()
        .bucket(bucketName)
        .prefix(prefix)
        .build();

    List<String> fileList = s3Client.listObjects(listObjects).contents().stream()
        .map(S3Object::key)
        .sorted()  // 🔹 파일명 정렬 (01_~, 02_~ 순서 유지)
        .collect(Collectors.toList());

    if (fileList.isEmpty()) {
        return "https://via.placeholder.com/500";  // 기본 이미지 반환
    }

    return baseUrl + "/" + fileList.get(0); 
  }

  public List<String> getDetailImages(Long pno) {
    String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    String prefix = "uploads/" + year + "/product/" + pno + "/detail/";

    ListObjectsRequest listObjects = ListObjectsRequest.builder()
        .bucket(bucketName)
        .prefix(prefix)
        .build();

    List<String> fileList = s3Client.listObjects(listObjects).contents().stream()
        .map(S3Object::key)
        .sorted()  // 🔹 파일명 정렬 (01_~, 02_~ 순서 유지)
        .collect(Collectors.toList());

    if (fileList.isEmpty()) {
        return List.of();  // 기본 이미지 반환
    }
    List<String> returnList = new ArrayList<>();
    for (int i = 0; i < Math.min(fileList.size(), 3); i++) {
      returnList.add(baseUrl + bucketName + "/" + fileList.get(i));
  }
    return returnList;
  }

  public String generateReviewImagePath(Long prno) {
    return reviewPathTemplate.replace("{prno}", String.valueOf(prno));
  }

  public S3Service(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  @PostConstruct
  public void init() {
    log.info(" S3Service 초기화 완료");
    log.info(" 사용 중인 S3 Bucket: {}", bucketName);
    log.info(" Base URL: {}", baseUrl);
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

        // ✅ 버킷명이 포함된 올바른 URL 생성
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
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
