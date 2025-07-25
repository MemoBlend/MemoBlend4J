package com.memoblend.applicationcore.auth;

import org.springframework.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザーロール情報を表すクラスです。
 */
@Data
@NoArgsConstructor
public class Role {
  @NonNull
  private int id;
  @NonNull
  private String name;
}
