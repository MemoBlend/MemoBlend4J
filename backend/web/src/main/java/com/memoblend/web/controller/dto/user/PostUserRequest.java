package com.memoblend.web.controller.dto.user;

import lombok.Data;

/**
 * ユーザーの登録リクエストクラスです。
 */
@Data
public class PostUserRequest {
  private final String name;
  private final String authId;
}
