package com.memoblend.web.controller.mapper.user;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.web.controller.dto.user.GetUserResponse;

/**
 * {@link AppUser} を {@link GetUserResponse} に変換するクラスです。
 */
public class GetUserResponseMapper {

  /**
   * {@link AppUser} を {@link GetUserResponse} に変換します。
   * 
   * @param user ユーザー。
   * @return {@link GetUserResponse} のインスタンス。
   */
  public static GetUserResponse convert(AppUser user) {
    return new GetUserResponse(
        user.getId(),
        user.getName(),
        user.getAuthId());
  }

  // インスタンス化防止
  private GetUserResponseMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
