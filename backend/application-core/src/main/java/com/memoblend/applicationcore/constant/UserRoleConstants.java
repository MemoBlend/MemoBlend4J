package com.memoblend.applicationcore.constant;

/**
 * ユーザーのロール名を定義するクラスです。
 */
public class UserRoleConstants {
  /** 管理者ユーザーのロール名です。 */
  public static final String ADMIN = "ROLE_ADMIN";

  /** 一般ユーザーのロール名です。 */
  public static final String USER = "ROLE_USER";

  // Prevent instantiation
  private UserRoleConstants() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
