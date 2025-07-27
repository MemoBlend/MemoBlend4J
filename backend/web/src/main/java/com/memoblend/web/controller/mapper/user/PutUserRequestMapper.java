package com.memoblend.web.controller.mapper.user;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserValidationException;
import com.memoblend.web.controller.dto.user.PutUserRequest;

/**
 * {@link PutUserRequest} から {@link AppUser} への変換を行うクラスです。
 */
public class PutUserRequestMapper {

  /**
   * {@link PutUserRequest} から {@link AppUser} へ変換します。
   * 
   * @param request ユーザーの更新リクエスト。
   * @return ユーザー。
   * @throws AppUserValidationException ユーザーが不正な場合。
   */
  public static AppUser convert(PutUserRequest request) throws AppUserValidationException {
    return new AppUser(
        request.getId(),
        request.getName(),
        false,
        request.getAuthId());
  }

  // インスタンス化防止
  private PutUserRequestMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
