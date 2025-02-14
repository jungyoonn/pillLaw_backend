package com.eeerrorcode.pilllaw.util;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PostClean {

  private String postClean;
  private String postCleanCompany;
  private String postCleanExpiryDate;
  private String postCleanPrimary;
  private String postCleanNotice;
  private String postCleanKeep;
  private String postCleanRaw;
}
