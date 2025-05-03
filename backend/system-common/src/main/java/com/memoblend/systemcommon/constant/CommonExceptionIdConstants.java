package com.memoblend.systemcommon.constant;

/**
 * 例外 ID 用の定数クラスです。
 */
public class CommonExceptionIdConstants {
  /** 想定外のシステムエラーが発生しました。 */
  public static final String E_SYSTEM = "systemError";

  /** 想定外の業務エラーが発生しました。 */
  public static final String E_BUSINESS = "businessError";

  // インスタンス化防止
  private CommonExceptionIdConstants() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
