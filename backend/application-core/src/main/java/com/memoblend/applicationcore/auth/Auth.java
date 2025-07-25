package com.memoblend.applicationcore.auth;

import java.util.List;
import org.springframework.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 認証情報を表すクラスです。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
  @NonNull
  private String id;
  @NonNull
  private String passwordHash;
  @NonNull
  private List<UserRole> userRoles;
}
