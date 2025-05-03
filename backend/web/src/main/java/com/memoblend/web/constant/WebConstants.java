package com.memoblend.web.constant;

/**
 * web プロジェクトで利用する汎用定数クラスです。
 */
public class WebConstants {

  /** Exception ID 。 */
  public static final String PROBLEM_DETAILS_EXCEPTION_ID = "exceptionId";

  /** Exception ID に紐づく例外値。 */
  public static final String PROBLEM_DETAILS_EXCEPTION_VALUES = "exceptionValues";

  // インスタンス化防止
  private WebConstants() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
