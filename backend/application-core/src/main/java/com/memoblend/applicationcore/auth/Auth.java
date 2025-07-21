package com.memoblend.applicationcore.auth;

import java.util.List;
import org.springframework.lang.NonNull;
import lombok.Data;

/**
 * 認証情報を表すクラスです。
 */
@Data
public class Auth {
  @NonNull
  private String id;
  @NonNull
  private String passwordHash;
  @NonNull
  private List<Role> roles;
}
