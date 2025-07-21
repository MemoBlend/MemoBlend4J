package com.memoblend.applicationcore.auth;

import java.util.List;

/**
 * ログインしているユーザーの情報を保持するためのインターフェースです。
 */
public interface UserStore {

  /**
   * ログインしているユーザーの認証IDを取得します。
   *
   * @return 認証ID。
   */
  public String getAuthId();

  /**
   * ログインしているユーザーのロール名を取得します。
   */
  public List<String> getUserRoles();

  /**
   * ログインしているユーザーが指定されたロールに所属しているかどうかを判定します。
   */
  public boolean isInRole(String role);
}
