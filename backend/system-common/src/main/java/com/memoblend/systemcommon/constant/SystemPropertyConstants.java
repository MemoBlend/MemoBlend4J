package com.memoblend.systemcommon.constant;

/**
 * ロガーの定数クラスです。
 */
public class SystemPropertyConstants {
  /** アプリケーションロガーの名前です。 */
  public static final String APPLICATION_LOGGER = "application.log";

  /** 改行文字です。 */
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  // インスタンス化防止
  private SystemPropertyConstants() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
