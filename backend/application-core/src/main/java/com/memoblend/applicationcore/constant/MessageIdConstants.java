package com.memoblend.applicationcore.constant;

/**
 * 業務メッセージ ID 用の定数クラスです。
 */
public class MessageIdConstants {

  /** {}年{}月の日記の一覧を取得します。 */
  public static final String D_DIARY_GET_DIARIES_BY_YEAR_AND_MONTH = "diaryApplicationServiceGetDiariesByYearAndMonth";

  /** ユーザーID：{0} の日記の一覧を取得します。 */
  public static final String D_DIARY_GET_DIARIES_BY_USER_ID = "diaryApplicationServiceGetDiariesByUserId";

  /** 日記ID:{0}の日記を取得します。 */
  public static final String D_DIARY_GET_DIARY = "diaryApplicationServiceGetDiary";

  /** {0}年{1}月{2}日の日記を追加します。 */
  public static final String D_DIARY_ADD_DIARY = "diaryApplicationServiceAddDiary";

  /** 日記ID:{0}の日記を更新します。 */
  public static final String D_DIARY_UPDATE_DIARY = "diaryApplicationServiceUpdateDiary";

  /** 日記ID:{0}の日記を削除します。 */
  public static final String D_DIARY_DELETE_DIARY = "diaryApplicationServiceDeleteDiary";

  /** ユーザーの一覧を取得します。 */
  public static final String D_USER_GET_USERS = "userApplicationServiceGetUsers";

  /** ユーザーID：{0}のユーザーを取得します。 */
  public static final String D_USER_GET_USER = "userApplicationServiceGetUser";

  /** ユーザーID：{0}のユーザーを追加します。 */
  public static final String D_USER_ADD_USER = "userApplicationServiceAddUser";

  /** ユーザーID：{0}のユーザーを更新します。 */
  public static final String D_USER_UPDATE_USER = "userApplicationServiceUpdateUser";

  /** ユーザーID：{0}のユーザーを削除します。 */
  public static final String D_USER_DELETE_USER = "userApplicationServiceDeleteUser";

  /** 分析APIにユーザーID：{0} 日記ID：{1}の日記を登録します。 */
  public static final String D_ANALYTICS_REGISTER_DIARY = "analysisApiRegisterDiary";

  /** 分析APIにユーザーID：{0}の日記のおすすめスケジュールを取得します。 */
  public static final String D_ANALYTICS_GET_RECOMMENDED_SCHEDULE = "analysisApiGetRecommendedSchedule";

  // インスタンス化防止
  private MessageIdConstants() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
