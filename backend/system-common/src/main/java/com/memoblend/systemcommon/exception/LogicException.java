package com.memoblend.systemcommon.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 業務例外を表す例外クラスです。
 */
@Getter
@Setter
public class LogicException extends Exception {

  private final String exceptionId;

  private final String[] frontMessageValue;

  private final String[] logMessageValue;

  /**
   * 原因例外、例外 ID 、メッセージ用プレースホルダー（フロント用）、メッセージ用プレースホルダー（ログ用）を指定して、
   * {@link LogicException} クラスのインスタンスを初期化します。
   *
   * @param cause             原因例外。
   * @param exceptionId       例外 ID 。
   * @param frontMessageValue メッセージ用プレースホルダー（フロント用）。
   * @param logMessageValue   メッセージ用プレースホルダー（ログ用）。
   */
  public LogicException(Throwable cause, String exceptionId,
      String[] frontMessageValue, String[] logMessageValue) {
    super(cause);
    this.exceptionId = exceptionId;
    this.frontMessageValue = frontMessageValue;
    this.logMessageValue = logMessageValue;
  }
}
