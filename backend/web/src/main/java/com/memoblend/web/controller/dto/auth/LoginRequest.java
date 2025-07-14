package com.memoblend.web.controller.dto.auth;

import lombok.Data;

/**
 * ログインリクエストクラス。
 */
@Data
public class LoginRequest {
  private String authId;
  private String password;
}
