package com.eeerrorcode.pilllaw.service.pay;

import java.util.Map;

public interface IamportService {
  String getAccessToken();
  Map<String, Object> validatePayment(String impUid);
  Map<String, Object> cancelPayment(String impUid, String reason);
}
