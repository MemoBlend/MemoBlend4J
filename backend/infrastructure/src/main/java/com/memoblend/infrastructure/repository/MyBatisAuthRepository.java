package com.memoblend.infrastructure.repository;

import org.springframework.stereotype.Repository;
import com.memoblend.applicationcore.auth.Auth;
import com.memoblend.applicationcore.auth.AuthRepository;
import com.memoblend.infrastructure.repository.mapper.AuthMapper;
import lombok.AllArgsConstructor;

/**
 * MyBatisを使用した認証情報のリポジトリ実装クラスです。
 */
@Repository
@AllArgsConstructor
public class MyBatisAuthRepository implements AuthRepository {

  private final AuthMapper authMapper;

  @Override
  public Auth findById(String id) {
    return authMapper.findById(id);
  }
}
