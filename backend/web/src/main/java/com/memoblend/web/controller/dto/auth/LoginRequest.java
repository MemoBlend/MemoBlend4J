package com.memoblend.web.controller.dto.auth;

import lombok.Data;

/**
 * ログインリクエストクラス。
 */
@Data
public class LoginRequest {
  private final String authId;
  private final String password;
}
