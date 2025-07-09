package com.memoblend.web.controller.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ログインレスポンスクラス。
 * JWTトークンとユーザー情報を含むレスポンスを表します。
 */
@Data
@AllArgsConstructor
public class LoginResponse {
  private String token;
  private String tokenType;
  private String username;
  private Object authorities;
}
