package com.memoblend.applicationcore.user;

import com.memoblend.systemcommon.exception.ValidationException;

/**
 * ユーザーのバリデーションエラー時の例外です。
 */
public class UserValidationException extends ValidationException {

  /**
   * 例外 ID 、メッセージ用プレースホルダー（フロント用）、
   * メッセージ用プレースホルダー（ログ用）を指定して、
   * {@link UserValidationException} クラスのインスタンスを初期化します。
   * 
   * @param exceptionId       例外 ID 。
   * @param frontMessageValue メッセージ用プレースホルダー（フロント用）。
   * @param logMessageValue   メッセージ用プレースホルダー（ログ用）。
   */
  public UserValidationException(String exceptionId, String[] frontMessageValue, String[] logMessageValue) {
    super(exceptionId, frontMessageValue, logMessageValue);
  }
}
