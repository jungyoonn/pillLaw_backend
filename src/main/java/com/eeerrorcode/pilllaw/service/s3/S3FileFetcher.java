// package com.eeerrorcode.pilllaw.service.s3;

// import org.springframework.stereotype.Service;

// import lombok.AllArgsConstructor;
// import lombok.RequiredArgsConstructor;

// @Service
// @AllArgsConstructor
// public class S3FileFetcher {
//   private final AmazonS3 s3Client;

//     @Value("${cloud.aws.s3.bucket}")
//     private String bucketName;

//     /**
//      * 특정 상품(pno)의 첫 번째 이미지를 가져오는 메서드
//      * @param pno 상품 번호
//      * @return 첫 번째 파일 URL (없으면 null 반환)
//      */
//     public String getFirstProductImage(Long pno) {
//         String prefix = "uploads/2025/product/" + pno + "/detail/";
//         ObjectListing objectListing = s3Client.listObjects(bucketName, prefix);
//         List<S3ObjectSummary> fileList = objectListing.getObjectSummaries();

//         // 파일이 있는 경우 첫 번째 파일 URL 반환
//         return fileList.stream()
//                 .map(file -> s3Client.getUrl(bucketName, file.getKey()).toString())
//                 .findFirst()
//                 .orElse(null);
//     }
// }
