package com.memoblend.infrastructure.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.memoblend.applicationcore.user.User;

/**
 * ユーザーの OR マッパークラスです。
 */
@Mapper
public interface UserMapper {

  /**
   * ユーザーを全件取得します。
   * 
   * @return 全てのユーザー。
   */
  public List<User> findAll();

  /**
   * ID を指定して、ユーザーを取得します。
   * 
   * @param id ユーザーの ID 。
   * @return 条件に合うユーザー。
   */
  public User findById(long id);

  /**
   * 認証 ID を指定して、ユーザーを取得します。
   * 
   * @param authId 認証 ID 。
   * @return 条件に合うユーザー。
   */
  public User findByAuthId(String authId);

  /**
   * ユーザーを追加します。
   * 
   * @param user 追加するユーザー。
   * @return 追加したユーザーの数。
   */
  public long add(User user);

  /**
   * ID を指定して、ユーザーを削除します。
   * 
   * @param id 削除するユーザーの ID 。
   */
  public long delete(long id);

  /**
   * ユーザーを更新します。
   * 
   * @param user 更新するユーザー。
   */
  public long update(User user);
}
