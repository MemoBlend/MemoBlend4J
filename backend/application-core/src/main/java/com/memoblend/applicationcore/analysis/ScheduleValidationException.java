package com.memoblend.applicationcore.analysis;

import com.memoblend.systemcommon.exception.ValidationException;

/**
 * スケジュールのバリデーション例外クラスです。
 */
public class ScheduleValidationException extends ValidationException {

  /**
   * 例外 ID 、メッセージ用プレースフォルダ（フロント用）、
   * メッセージ用プレースフォルダ（ログ用）を指定して、
   * {@link ScheduleValidationException} クラスのインスタンスを初期化します。
   * 
   * @param exceptionId       例外 ID 。
   * @param frontMessageValue メッセージ用プレースフォルダ（フロント用）。
   * @param logMessageValue   メッセージ用プレースフォルダ（ログ用）。
   */
  public ScheduleValidationException(String exceptionId, String[] frontMessageValue, String[] logMessageValue) {
    super(exceptionId, frontMessageValue, logMessageValue);
  }
}
