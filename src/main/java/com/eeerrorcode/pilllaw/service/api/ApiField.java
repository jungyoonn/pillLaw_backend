package com.eeerrorcode.pilllaw.service.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiField {
  private String keyId;  
  private String serviceId;
  private String dataType;
  private String startIdx;
  private String endIdx;
  private String itemName;
  private String companyName;
  private String encodedItemName;
}
