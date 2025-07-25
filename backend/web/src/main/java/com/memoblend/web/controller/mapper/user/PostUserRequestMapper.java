package com.memoblend.web.controller.mapper.user;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserValidationException;
import com.memoblend.web.controller.dto.user.PostUserRequest;

/**
 * {@link PostUserRequest} を {@link AppUser} に変換するクラスです。
 */
public class PostUserRequestMapper {

  /**
   * {@link PostUserRequest} を {@link AppUser} に変換します。
   * 
   * @param request リクエスト。
   * @return ユーザー。
   * @throws AppUserValidationException ユーザーが不正な場合。
   */
  public static AppUser convert(PostUserRequest request) throws AppUserValidationException {
    return new AppUser(
        0,
        request.getName(),
        false,
        request.getAuthId());
  }

  // インスタンス化防止
  private PostUserRequestMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
