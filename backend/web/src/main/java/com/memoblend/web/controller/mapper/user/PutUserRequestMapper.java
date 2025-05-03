package com.memoblend.web.controller.mapper.user;

import com.memoblend.applicationcore.user.User;
import com.memoblend.applicationcore.user.UserValidationException;
import com.memoblend.web.controller.dto.user.PutUserRequest;

/**
 * {@link PutUserRequest} から {@link User} への変換を行うクラスです。
 */
public class PutUserRequestMapper {

  // Prevent instantiation
  private PutUserRequestMapper() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * {@link PutUserRequest} から {@link User} へ変換します。
   * 
   * @param request ユーザーの更新リクエスト。
   * @return ユーザー。
   * @throws UserValidationException ユーザーが不正な場合。
   */
  public static User convert(PutUserRequest request) throws UserValidationException {
    return new User(
        request.getId(),
        request.getName(),
        false);
  }
}
