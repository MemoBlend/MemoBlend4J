package com.memoblend.applicationcore.constant;

/**
 * ユーザーのロール名を定義するクラスです。
 */
public class UserRoleConstants {
  /** 管理者ユーザーのロール名です。 */
  public static final String ADMIN = "ROLE_ADMIN";

  /** 一般ユーザーのロール名です。 */
  public static final String USER = "ROLE_USER";

  // インスタンス化防止
  private UserRoleConstants() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
