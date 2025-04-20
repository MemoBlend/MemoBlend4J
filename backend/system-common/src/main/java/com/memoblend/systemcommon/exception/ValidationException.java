package com.memoblend.systemcommon.exception;

/**
 * バリデーションエラー時の例外です。
 */
public class ValidationException extends LogicException {
  /**
   * 例外 ID 、メッセージ用プレースホルダー（フロント用）、
   * メッセージ用プレースホルダー（ログ用）を指定して、
   * {@link ValidationException} クラスのインスタンスを初期化します。
   * 
   * @param exceptionId       例外 ID 。
   * @param frontMessageValue メッセージ用プレースホルダー（フロント用）。
   * @param logMessageValue   メッセージ用プレースホルダー（ログ用）。
   */
  public ValidationException(String exceptionId, String[] frontMessageValue, String[] logMessageValue) {
    super(null, exceptionId, frontMessageValue, logMessageValue);
  }
}
