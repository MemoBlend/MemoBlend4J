package com.memoblend.applicationcore.diary;

import com.memoblend.systemcommon.exception.ValidationException;

/**
 * 日記のバリデーションエラー時の例外です。
 */
public class DiaryValidationException extends ValidationException {

  /**
   * 例外 ID 、メッセージ用プレースホルダー（フロント用）、
   * メッセージ用プレースホルダー（ログ用）を指定して、
   * {@link DiaryValidationException} クラスのインスタンスを初期化します。
   * 
   * @param exceptionId       例外 ID 。
   * @param frontMessageValue メッセージ用プレースホルダー（フロント用）。
   * @param logMessageValue   メッセージ用プレースホルダー（ログ用）。
   */
  public DiaryValidationException(String exceptionId, String[] frontMessageValue, String[] logMessageValue) {
    super(exceptionId, frontMessageValue, logMessageValue);
  }
}
