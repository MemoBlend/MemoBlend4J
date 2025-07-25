package com.memoblend.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memoblend.applicationcore.auth.Auth;

@Mapper
public interface JoinedAuthMapper {

  /**
   * 指定された ID の認証情報を取得します。
   *
   * @param id 認証情報の ID 。
   * @return 認証情報。
   */
  Auth findById(@Param("id") String id);
}
