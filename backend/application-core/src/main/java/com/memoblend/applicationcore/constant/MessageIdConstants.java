package com.memoblend.applicationcore.constant;

/**
 * 業務メッセージ ID 用の定数クラスです。
 */
public class MessageIdConstants {

  /** 日記の一覧を取得します。 */
  public static final String D_DIARY_GET_DIARIES = "diaryApplicationServiceGetDiaries";

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
}
