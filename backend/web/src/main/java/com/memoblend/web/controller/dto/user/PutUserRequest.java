package com.memoblend.web.controller.dto.user;

import lombok.Data;

/**
 * ユーザーの更新リクエストクラスです。
 */
@Data
public class PutUserRequest {
  private final long id;
  private final String name;
  private final String authId;
}
