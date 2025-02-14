package com.eeerrorcode.pilllaw.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class Api {
   // 건강기능식품 품목제조신고(원재료)
    //(https://www.foodsafetykorea.go.kr/api/newDatasetDetail.do)

    public static void main(String[] args) throws IOException {
        // API 요청 변수 설정
        String keyId = "286a3e3b38cf4050b678"; // API 인증키
        String serviceId = "C003"; // 서비스 ID
        String dataType = "xml"; // 응답 데이터 형식
        String startIdx = "1"; // 요청 시작 위치
        String endIdx = "15"; // 요청 종료 위치 // 검색 결과 양 반환
        String itemName = "칼슘"; // 검색할 품목명 
        String companyName = ""; // 예시로, 업체명을 넣을 수 있음

        // 품목명 URL 인코딩
        String encodedItemName = URLEncoder.encode(itemName, "UTF-8");

        // 기본 URL 구성
        StringBuilder urlBuilder = new StringBuilder("http://openapi.foodsafetykorea.go.kr/api/");
        urlBuilder.append(keyId + "/" + serviceId + "/" + dataType + "/" + startIdx + "/" + endIdx);

        // 추가 요청 인자들 URL에 append
        if (!itemName.isEmpty()) {
            urlBuilder.append("/PRDLST_NM=" + encodedItemName); // 품목명 인자 추가
        }
        if (!companyName.isEmpty()) {
            urlBuilder.append("&BSSH_NM=" + URLEncoder.encode(companyName, "UTF-8")); // 업체명 인자 추가
        }

        // URL 객체 생성
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 응답 코드 확인
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == 200) {
            // 응답 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 응답 데이터 출력 (전체 확인)
            // System.out.println("응답 데이터:");
            // System.out.println(response.toString());

            // XML 파싱
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(response.toString())));
                document.getDocumentElement().normalize();

                // 품목명 검색 결과 출력
                System.out.println("품목명 검색 결과:");
                NodeList rowList = document.getElementsByTagName("row");
                if (rowList.getLength() == 0) {
                    System.out.println("검색된 품목명이 없습니다.");
                } else {
                    for (int i = 0; i < rowList.getLength(); i++) {
                        Node rowNode = rowList.item(i);
                        if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element rowElement = (Element) rowNode;
                            System.out.println("========================================================");
                            // 각 row에서 품목명 정보 출력
                            String productName = getTagValue("PRDLST_NM", rowElement);
                            System.out.println("품목명: " + productName);
                            
                            // 추가 정보 출력 (예: 제조사, 유통기한 등)
                            String company = getTagValue("BSSH_NM", rowElement);
                            String expiryDate = getTagValue("POG_DAYCNT", rowElement);
                            String primary = getTagValue("PRIMARY_FNCLTY", rowElement);
                            String notice = getTagValue("IFTKN_ATNT_MATR_CN", rowElement);
                            String keep = getTagValue("CSTDY_MTHD", rowElement);
                            String raw = getTagValue("RAWMTRL_NM", rowElement);
                            System.out.println("제조사: " + company);
                            System.out.println("유통기한: " + expiryDate);
                            System.out.println("기능성 : " + primary);
                            System.out.println("섭취시 주의사항 : " + notice);
                            System.out.println("보관방법 : " + keep);
                            System.out.println("원재료 : " + raw);
                            System.out.println("========================================================");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("XML 파싱 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("API 호출 실패: " + responseCode);
        }

        // 연결 종료
        connection.disconnect();
    }

    // XML 태그에서 값 추출하는 헬퍼 메소드
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }
}
