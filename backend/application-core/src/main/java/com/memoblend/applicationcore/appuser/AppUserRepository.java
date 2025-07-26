package com.memoblend.applicationcore.appuser;

import java.util.List;

/**
 * ユーザーのリポジトリのインターフェースです。
 */
public interface AppUserRepository {
  /**
   * 全てのユーザーを取得します。
   * 
   * @return 全てのユーザー。
   */
  List<AppUser> findAll();

  /**
   * ID を指定して、 {@link AppUser} を取得します。
   * 
   * @param id ユーザー ID 。
   * @return 条件に合うユーザー。
   */
  AppUser findById(long id);

  /**
   * 認証 ID を指定して、 {@link AppUser} を取得します。
   * 
   * @param authId 認証 ID 。
   * @return 条件に合うユーザー。
   */
  List<AppUser> findByAuthId(String authId);

  /**
   * ユーザーを追加します。
   * 
   * @param user 追加するユーザー。
   * @return 追加したユーザー。
   */
  AppUser add(AppUser user);

  /**
   * ID を指定して、 {@link AppUser} を削除します。
   * 
   * @param id 削除するユーザーの ID 。
   */
  long delete(long id);

  /**
   * {@link AppUser} を更新します。
   * 
   * @param user 更新するユーザー。
   */
  long update(AppUser user);
}
