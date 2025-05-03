package com.memoblend.web.controller.mapper.user;

import com.memoblend.applicationcore.user.User;
import com.memoblend.web.controller.dto.user.GetUserResponse;

/**
 * {@link User} を {@link GetUserResponse} に変換するクラスです。
 */
public class GetUserReponseMapper {

  // インスタンス化防止
  private GetUserReponseMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }

  /**
   * {@link User} を {@link GetUserResponse} に変換します。
   * 
   * @param user ユーザー。
   * @return {@link GetUserResponse} のインスタンス。
   */
  public static GetUserResponse convert(User user) {
    return new GetUserResponse(
        user.getId(),
        user.getName());
  }
}
