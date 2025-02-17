package com.eeerrorcode.pilllaw.util;

// import java.util.List;
import java.util.Set;

import com.eeerrorcode.pilllaw.dto.product.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DataCleaner {

  private final ProductDto dto;

  private static final Set<String> BIOACTIVE = Set.of(
    "기억력", "혈행", "간건강", "체지방", "갱년기 남", "갱년기 여",
    "혈당", "눈", "면역", "관절, 뼈", "전립선", "피로", "피부",
    "콜레스테롤", "혈압", "긴장", "장", "요로", "소화", "항산화",
    "혈중중성지방", "인지능력", "운동수행, 지구력", "치아", "배뇨",
    "면역과민 피부", "월경", "정자", "질 유산균", "유아 성장"
  );

  private static final Set<String> NUTRIENT = Set.of(
    "비타민 A", "비타민 B", "비타민 D", "비타민 E", "비타민 K",
    "비타민 B1", "비타민 B2", "비타민 B6", "비타민 B12", "비타민 C",
    "나이아신", "엽산", "비오틴", "칼슘", "마그네슘", "철", "아연", "구리",
    "셀레늄", "요오드", "망간", "몰리브덴", "칼륨", "크롬", "식이섬유",
    "단백질", "필수 지방산"
    );


  private String cleanText(PreClean preClean){
    // if (method preClean.getClass().getMethod("*", null).getName().contains("get")){};
    return null;
  }

}
