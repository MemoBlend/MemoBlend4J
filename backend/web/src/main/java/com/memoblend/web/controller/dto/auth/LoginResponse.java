package com.memoblend.web.controller.dto.auth;

import lombok.Data;

/**
 * ログインレスポンスクラス。
 * JWTトークンとユーザー情報を含むレスポンスを表します。
 */
@Data
public class LoginResponse {
  private final String token;
  private final String tokenType;
  private final String userName;
  private final Object authorities;
}
