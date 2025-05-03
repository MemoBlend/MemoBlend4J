package com.memoblend.applicationcore.constant;

/**
 * 業務例外 ID 用の定数クラスです。
 */
public class ExceptionIdConstants {

  /** 日記ID:{0}の日記が見つかりませんでした。 */
  public static final String E_DIARY_NOT_FOUND = "diaryNotFound";

  /** 日記の{0}は必須です。 */
  public static final String E_DIARY_FIELD_IS_REQUIRED = "diaryFieldIsRequired";

  /** 日記の{0}は{1}~{2}の範囲で入力してください。 */
  public static final String E_DIARY_VALUE_IS_OUT_OF_RANGE = "diaryValueIsOutOfRange";

  /** ユーザーID：{0}のユーザーが見つかりませんでした。 */
  public static final String E_USER_NOT_FOUND = "userNotFound";

  /** ユーザーの{0}は必須です。 */
  public static final String E_USER_FIELD_IS_REQUIRED = "userFieldIsRequired";

  /** ユーザーの{0}は{1}~{2}の範囲で入力してください。 */
  public static final String E_USER_VALUE_IS_OUT_OF_RANGE = "userValueIsOutOfRange";

  /** {0} を実行する権限がありません。 */
  public static final String E_PERMISSION_DENIED = "permissionDenied";

  // Prevent instantiation
  private ExceptionIdConstants() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
