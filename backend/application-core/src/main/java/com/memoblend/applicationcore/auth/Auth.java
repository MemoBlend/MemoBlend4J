package com.memoblend.applicationcore.auth;

import lombok.Data;

/**
 * 認証情報を表すクラスです。
 */
@Data
public class Auth {
  private String id;
  private String password;
  private String userRole;
  private boolean isDeleted;
}
