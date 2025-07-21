package com.memoblend.web.controller.dto.auth;

import lombok.Data;

/**
 * トークンの有効性検証結果を表すレスポンスクラスです。
 */
@Data
public class TokenValidationResponse {
  private final boolean valid;
}
