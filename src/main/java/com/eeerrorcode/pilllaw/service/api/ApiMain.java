package com.eeerrorcode.pilllaw.service.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.eeerrorcode.pilllaw.util.PreClean;

public class ApiMain {
    // 건강기능식품 품목제조신고(원재료)
    //(https://www.foodsafetykorea.go.kr/api/newDatasetDetail.do)
    private static final String KEYID = "286a3e3b38cf4050b678";
    private static final String SERVICEID = "C003"; 
    private static final String DATATYPE = "xml"; 
    private static final String STARTIDX = "1"; 
    private static final String ENDIDX = "15"; 
    private static final String ITEMNAME = "칼슘"; 
    private static final String COMPANYNAME = ""; 
    public static void main(String[] args) throws IOException {

        ApiField apiField = ApiField.builder()
        .keyId(KEYID)
            .serviceId(SERVICEID)
            .dataType(DATATYPE)
            .startIdx(STARTIDX)
            .endIdx(ENDIDX)
            .itemName(ITEMNAME)
            .companyName(COMPANYNAME)
            .encodedItemName(URLEncoder.encode(ITEMNAME, "UTF-8"))
        .build();

        ApiCaller apiCaller = new ApiCaller();

        URL url = apiCaller.buildApiCallUrl(apiField);
        List<PreClean> apiResultList = apiCaller.sendAndGetFromApi(url);

    }
}
