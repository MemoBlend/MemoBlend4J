package com.memoblend.applicationcore.user;

import java.util.List;

/**
 * ユーザーのリポジトリのインターフェースです。
 */
public interface UserRepository {
  /**
   * 全てのユーザーを取得します。
   * 
   * @return 全てのユーザー。
   */
  List<User> findAll();

  /**
   * ID を指定して、 {@link User} を取得します。
   * 
   * @param id ユーザー ID 。
   * @return 条件に合うユーザー。
   */
  User findById(long id);

  /**
   * 認証 ID を指定して、 {@link User} を取得します。
   * 
   * @param authId 認証 ID 。
   * @return 条件に合うユーザー。
   */
  User findByAuthId(String authId);

  /**
   * ユーザーを追加します。
   * 
   * @param user 追加するユーザー。
   * @return 追加したユーザー。
   */
  User add(User user);

  /**
   * ID を指定して、 {@link User} を削除します。
   * 
   * @param id 削除するユーザーの ID 。
   */
  long delete(long id);

  /**
   * {@link User} を更新します。
   * 
   * @param user 更新するユーザー。
   */
  long update(User user);
}
