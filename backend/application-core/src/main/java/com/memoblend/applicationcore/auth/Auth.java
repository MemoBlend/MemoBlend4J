package com.memoblend.applicationcore.auth;

import java.util.List;
import lombok.Data;

/**
 * 認証情報を表すクラスです。
 */
@Data
public class Auth {
  private String id;
  private String passwordHash;
  private List<Role> roles;
}
