package com.memoblend.web.controller.mapper.user;

import com.memoblend.applicationcore.user.User;
import com.memoblend.applicationcore.user.UserValidationException;
import com.memoblend.web.controller.dto.user.PostUserRequest;

/**
 * {@link PostUserRequest} を {@link User} に変換するクラスです。
 */
public class PostUserRequestMapper {

  /**
   * {@link PostUserRequest} を {@link User} に変換します。
   * 
   * @param request リクエスト。
   * @return ユーザー。
   * @throws UserValidationException ユーザーが不正な場合。
   */
  public static User convert(PostUserRequest request) throws UserValidationException {
    User user = new User(
        0,
        request.getName(),
        false);
    return user;
  }
}
