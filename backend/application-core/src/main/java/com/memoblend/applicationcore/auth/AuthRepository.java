package com.memoblend.applicationcore.auth;

/**
 * 認証情報のリポジトリインターフェースです。
 */
public interface AuthRepository {

  /**
   * ユーザーIDを指定して、認証情報を取得します。
   *
   * @param id ユーザーID。
   * @return 条件に合う認証情報。
   */
  public Auth findById(String id);
}
