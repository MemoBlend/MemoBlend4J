package com.memoblend.infrastructure.repository;

import org.springframework.stereotype.Repository;
import com.memoblend.applicationcore.auth.Auth;
import com.memoblend.applicationcore.auth.AuthRepository;
import com.memoblend.infrastructure.mapper.JoinedAuthMapper;
import lombok.AllArgsConstructor;

/**
 * 認証情報のリポジトリ実装クラスです。
 */
@Repository
@AllArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {
  private final JoinedAuthMapper joinedAuthMapper;

  @Override
  public Auth findById(String id) {
    Auth auth = joinedAuthMapper.findById(id);
    return auth;
  }
}
