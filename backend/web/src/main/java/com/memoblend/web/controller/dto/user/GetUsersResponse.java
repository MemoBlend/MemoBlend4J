package com.memoblend.web.controller.dto.user;

import java.util.List;

import com.memoblend.applicationcore.appuser.AppUser;

import lombok.Data;

/**
 * ユーザーのリスト取得レスポンスクラスです。
 */
@Data
public class GetUsersResponse {
  private final List<AppUser> users;
}
