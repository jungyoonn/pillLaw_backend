package com.eeerrorcode.pilllaw.dto.product;

import com.eeerrorcode.pilllaw.entity.product.PPackage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PPackageDto {
  
  private Long pckNo;

  private String pckName;

  private Long salePrice;

  private List<String> type; 
  private List<PPackageDto> packList;

  public PPackageDto(PPackage pck) {
    this.pckNo = pck.getPckNo();
    this.pckName = pck.getPckName();
    this.salePrice = pck.getSalePrice();
    this.type = pck.getTypeSet().stream().map(Enum::name).collect(Collectors.toList());
    this.packList = List.of(); 
  }

  private LocalDateTime regDate, modDate;
}
