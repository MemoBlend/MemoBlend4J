package com.memoblend.applicationcore.auth;

import org.springframework.lang.NonNull;
import lombok.Data;

/**
 * ユーザーロール情報を表すクラスです。
 */
@Data
public class Role {
  @NonNull
  private int id;
  @NonNull
  private String name;
}
