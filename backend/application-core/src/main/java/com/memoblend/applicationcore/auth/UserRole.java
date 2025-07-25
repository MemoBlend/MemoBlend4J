package com.memoblend.applicationcore.auth;

import org.springframework.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザーロール情報を表すクラスです。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
  @NonNull
  private int id;
  @NonNull
  private String name;
}
