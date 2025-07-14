package com.memoblend.infrastructure.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.memoblend.applicationcore.auth.Auth;

/**
 * 認証情報に関するMyBatisマッパーインターフェースです。
 */
@Mapper
public interface AuthMapper {
  /**
   * ユーザーIDを指定して、認証情報を取得します。
   * 
   * @param id ユーザーID。
   * @return 認証情報。
   */
  public Auth findById(String id);
}
