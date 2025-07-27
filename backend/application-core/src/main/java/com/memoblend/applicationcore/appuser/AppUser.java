package com.memoblend.applicationcore.appuser;

import org.springframework.lang.NonNull;
import com.memoblend.applicationcore.appuser.valueobject.Name;
import lombok.NoArgsConstructor;

/**
 * ユーザーのエンティティを表すクラスです。
 */
@NoArgsConstructor
public class AppUser {

  @NonNull
  private long id;
  @NonNull
  private Name name;
  @NonNull
  private boolean isDeleted;
  @NonNull
  private String authId;

  /**
   * ユーザーオブジェクトを生成します。
   *
   * @param id        ユーザーの ID 。
   * @param name      ユーザーの名前。
   * @param isDeleted 削除済みの場合 true 、 未削除の場合 false 。
   * @param authId    認証情報の ID 。
   * @throws AppUserValidationException ユーザーが不正な場合。
   */
  public AppUser(long id, String name, boolean isDeleted, String authId) throws AppUserValidationException {
    this.id = id;
    this.name = new Name(name);
    this.isDeleted = isDeleted;
    this.authId = authId;
  }

  /**
   * ユーザーのIDを取得します。
   *
   * @return ユーザーの ID 。
   */
  public long getId() {
    return this.id;
  }

  /**
   * ユーザーの名前を取得します。
   *
   * @return ユーザーの名前。
   */
  public String getName() {
    return this.name.getValue();
  }

  /**
   * ユーザーの削除状態を取得します。
   *
   * @return 削除済みの場合 true 、 未削除の場合 false 。
   */
  public boolean getIsDeleted() {
    return this.isDeleted;
  }

  /**
   * ユーザーの認証情報 ID を取得します。
   *
   * @return 認証情報の ID 。
   */
  public String getAuthId() {
    return this.authId;
  }

  /**
   * ユーザーのIDを設定します。
   *
   * @param id 新しいユーザー ID 。
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * ユーザーの名前を設定します。
   *
   * @param name 新しいユーザー名。
   * @throws AppUserValidationException ユーザー名が不正な場合。
   */
  public void setName(String name) throws AppUserValidationException {
    this.name = new Name(name);
  }

  /**
   * ユーザーの削除状態を設定します。
   *
   * @param isDeleted 削除済みの場合 true 、 未削除の場合 false 。
   */
  public void setIsDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  /**
   * ユーザーの認証情報 ID を設定します。
   *
   * @param authId 新しい認証情報 ID 。
   */
  public void setAuthId(String authId) {
    this.authId = authId;
  }
}