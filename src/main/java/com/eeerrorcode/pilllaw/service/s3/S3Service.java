package com.eeerrorcode.pilllaw.service.s3;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;


@Service
public class S3Service {
  
  private final S3Client s3Client;

  @Value("${aws.s3.bucket-name}")
  private String bucketName;

  @Value("${aws.s3.base-url}")
  private String baseUrl;

  public S3Service(S3Client s3Client){
    this.s3Client = s3Client;
  }

  public String uploadFile(MultipartFile file, String folder){
    String ext = getFileExtension(file.getOriginalFilename());
    String fileName = folder + "/" + UUID.randomUUID() + "." + ext;

    try{
      PutObjectRequest putObjectRequest = PutObjectRequest
      .builder()
        .bucket(bucketName)
        .key(fileName)
        .contentType(file.getContentType())
      .build();

      s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
      return baseUrl + "/" + fileName;
    }catch( S3Exception | IOException e){
      throw new RuntimeException("파일 업로드 실패!!!!!!", e);
    }
  }
  
  private String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
        throw new IllegalArgumentException("파일 확장자가 Not found 입니다 : " + fileName);
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }
}
