// package com.eeerrorcode.pilllaw.service.api;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.StringReader;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import java.net.URLEncoder;
// import java.util.ArrayList;
// import java.util.List;

// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;

// import org.springframework.web.util.UriComponentsBuilder;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;
// import org.xml.sax.InputSource;

// import com.eeerrorcode.pilllaw.util.PreClean;

// import lombok.Getter;
// import lombok.Setter;


// @Getter
// @Setter
// public class ApiCaller {

//   public URL buildApiCallUrl(ApiField apiField) throws IOException{
//     // StringBuilder urlBuilder = new StringBuilder("http://openapi.foodsafetykorea.go.kr/api/");
//     // urlBuilder.append(apiField.getKeyId()+ "/" + apiField.getServiceId() + "/" + apiField.getDataType() + "/" + apiField.getStartIdx() + "/" + apiField.getEndIdx());

//     // if (!apiField.getItemName().isEmpty()) {
//     //   urlBuilder.append("/PRDLST_NM=" + apiField.getEncodedItemName());
//     // }
//     // if (!apiField.getCompanyName().isEmpty()) {
//     //   urlBuilder.append("&BSSH_NM=" + URLEncoder.encode(apiField.getCompanyName(), "UTF-8")); 
//     // }
//     // URL url = new URL(urlBuilder.toString());
//     // return url;
//     return new URL(
//       UriComponentsBuilder.fromHttpUrl("http://openapi.foodsafetykorea.go.kr/api/")
//         .pathSegment(apiField.getKeyId(), apiField.getServiceId(), apiField.getDataType(), apiField.getStartIdx(), apiField.getEndIdx())
//         .queryParam("PRDLST_NM", apiField.getItemName().isEmpty() ? null : apiField.getEncodedItemName())
//         .queryParam("BSSH_NM", apiField.getCompanyName().isEmpty() ? null : URLEncoder.encode(apiField.getCompanyName(), "UTF-8"))
//         .toUriString()
//     );
//   }

//   public List<PreClean> sendAndGetFromApi(URL url) throws IOException{
//     List<PreClean> preCleanApiDatas = new ArrayList<>();
//     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//     connection.setRequestMethod("GET");
//     int responseCode = connection.getResponseCode();
//     System.out.println(responseCode);

//     if(responseCode == 200){
//       BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//       StringBuilder response = new StringBuilder();
//       String line;
//       while((line = reader.readLine()) != null){
//         response.append(line);
//       }
//       reader.close();
//     try {
//       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//       DocumentBuilder builder = factory.newDocumentBuilder();
//       Document document = builder.parse(new InputSource(new StringReader(response.toString())));
//       document.getDocumentElement().normalize();

//       NodeList rowList = document.getElementsByTagName("row");
//       if (rowList.getLength() == 0) {
//         System.out.println("nothing to return." + responseCode);
//       } else {
//           for (int i = 0; i < rowList.getLength(); i++) {
//             Node rowNode = rowList.item(i);
//             if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
//               Element rowElement = (Element) rowNode;
//               String productName = getTagValue("PRDLST_NM", rowElement);
//               String company = getTagValue("BSSH_NM", rowElement);
//               String expiryDate = getTagValue("POG_DAYCNT", rowElement);
//               String primary = getTagValue("PRIMARY_FNCLTY", rowElement);
//               String notice = getTagValue("IFTKN_ATNT_MATR_CN", rowElement);
//               String keep = getTagValue("CSTDY_MTHD", rowElement);
//               String raw = getTagValue("RAWMTRL_NM", rowElement);
//               preCleanApiDatas.add(PreClean
//               .builder()
//                 .preCleanProductName(productName)
//                 .preCleanCompany(company)
//                 .preCleanExpiryDate(expiryDate)
//                 .preCleanPrimary(primary)
//                 .preCleanNotice(notice)
//                 .preCleanKeep(keep)
//                 .preCleanRaw(raw)
//               .build());
//             }
//           }
//         }
//       } catch (Exception e) {
//           System.out.println(e.getMessage());
//       }
//     } else {
//         System.out.println(responseCode);
//     }
//     connection.disconnect();
    
//     if(preCleanApiDatas.size() == 0){
//       return null;
//     }
//     return preCleanApiDatas;
//   }

//   private static String getTagValue(String tag, Element element) {
//     NodeList nodeList = element.getElementsByTagName(tag);
//     if (nodeList.getLength() > 0) {
//         Node node = nodeList.item(0);
//         return node.getTextContent();
//     }
//     return null;
//   }
// }

