package com.memoblend.web.controller.mapper.user;

import java.util.List;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.web.controller.dto.user.GetUsersResponse;

/**
 * {@link AppUser} のリストを {@link GetUsersResponse} に変換するクラスです。
 */
public class GetUsersResponseMapper {

  /**
   * {@link AppUser} のリストを {@link GetUsersResponse} に変換します。
   * 
   * @param users ユーザーのリスト。
   * @return {@link GetUsersResponse} のインスタンス。
   */
  public static GetUsersResponse convert(List<AppUser> users) {
    return new GetUsersResponse(users);
  }

  // インスタンス化防止
  private GetUsersResponseMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
