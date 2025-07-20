package com.memoblend.applicationcore.user;

import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.systemcommon.exception.LogicException;

/**
 * ユーザーが見つからない場合の例外です。
 */
public class UserNotFoundException extends LogicException {
  /**
   * ID を指定して、{@link UserNotFoundException} クラスのインスタンスを初期化します。
   * 
   * @param id ユーザー ID 。
   */
  public UserNotFoundException(long id) {
    super(null, ExceptionIdConstants.E_USER_NOT_FOUND, new String[] { String.valueOf(id) },
        new String[] { String.valueOf(id) });
  }
}
