package com.memoblend.web.controller.dto.auth;

import lombok.Data;

/**
 * トークンバリデーションリクエストクラス。
 */
@Data
public class TokenValidationRequest {
  private String token;
}
