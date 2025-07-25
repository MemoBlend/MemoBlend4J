package com.memoblend.applicationcore.appuser;

import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.systemcommon.exception.LogicException;

/**
 * ユーザーが見つからない場合の例外です。
 */
public class AppUserNotFoundException extends LogicException {
  /**
   * ID を指定して、{@link AppUserNotFoundException} クラスのインスタンスを初期化します。
   * 
   * @param id ユーザー ID 。
   */
  public AppUserNotFoundException(long id) {
    super(null, ExceptionIdConstants.E_USER_NOT_FOUND, new String[] { String.valueOf(id) },
        new String[] { String.valueOf(id) });
  }

  /**
   * 認証 ID を指定して、{@link AppUserNotFoundException} クラスのインスタンスを初期化します。
   * 
   * @param authId 認証 ID 。
   */
  public AppUserNotFoundException(String authId) {
    super(null, ExceptionIdConstants.E_USER_NOT_FOUND_BY_AUTH_ID, new String[] { authId },
        new String[] { authId });
  }
}
