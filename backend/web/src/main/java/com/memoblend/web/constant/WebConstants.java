package com.memoblend.web.constant;

/**
 * web プロジェクトで利用する汎用定数クラスです。
 */
public class WebConstants {

  /** Exception ID 。 */
  public static final String PROBLEM_DETAILS_EXCEPTION_ID = "exceptionId";

  /** Exception ID に紐づく例外値。 */
  public static final String PROBLEM_DETAILS_EXCEPTION_VALUES = "exceptionValues";

  // Prevent instantiation
  private WebConstants() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
