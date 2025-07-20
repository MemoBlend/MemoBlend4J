package com.memoblend.applicationcore.auth;

import lombok.Data;

/**
 * ユーザーロール情報を表すクラスです。
 */
@Data
public class Role {
  private int id;
  private String name;
}
