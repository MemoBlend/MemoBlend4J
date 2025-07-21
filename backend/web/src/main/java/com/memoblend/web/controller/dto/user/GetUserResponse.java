package com.memoblend.web.controller.dto.user;

import lombok.Data;

/**
 * ユーザー情報の取得レスポンスクラスです。
 */
@Data
public class GetUserResponse {
  private final long id;
  private final String name;
  private final String authId;
}
