package com.memoblend.web.controller.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * トークンの有効性検証結果を表すレスポンスクラスです。
 */
@Data
@AllArgsConstructor
public class TokenValidationResponse {
  private boolean valid;
  private String username;
}
