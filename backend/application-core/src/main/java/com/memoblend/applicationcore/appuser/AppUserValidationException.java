package com.memoblend.applicationcore.appuser;

import com.memoblend.systemcommon.exception.ValidationException;

/**
 * ユーザーのバリデーションエラー時の例外です。
 */
public class AppUserValidationException extends ValidationException {

  /**
   * 例外 ID 、メッセージ用プレースホルダー（フロント用）、
   * メッセージ用プレースホルダー（ログ用）を指定して、
   * {@link AppUserValidationException} クラスのインスタンスを初期化します。
   * 
   * @param exceptionId       例外 ID 。
   * @param frontMessageValue メッセージ用プレースホルダー（フロント用）。
   * @param logMessageValue   メッセージ用プレースホルダー（ログ用）。
   */
  public AppUserValidationException(String exceptionId, String[] frontMessageValue, String[] logMessageValue) {
    super(exceptionId, frontMessageValue, logMessageValue);
  }
}
