package com.memoblend.web.controller.dto.user;

import java.util.List;
import com.memoblend.applicationcore.user.User;
import lombok.Data;

/**
 * ユーザーのリスト取得レスポンスクラスです。
 */
@Data
public class GetUsersResponse {
  private final List<User> users;
}
