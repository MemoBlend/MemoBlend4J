package com.memoblend.infrastructure.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.memoblend.applicationcore.user.User;
import com.memoblend.applicationcore.user.UserRepository;
import com.memoblend.infrastructure.mybatis.generated.mapper.UserMapper;

import lombok.AllArgsConstructor;

/**
 * ユーザーのリポジトリ実装クラスです。
 */
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserMapper userMapper;

  @Override
  public List<User> findAll() {
    return userMapper.findAll();
  }

  @Override
  public User findById(long id) {
    return userMapper.findById(id);
  }

  @Override
  public User findByAuthId(String authId) {
    return userMapper.findByAuthId(authId);
  }

  @Override
  public User add(User user) {
    userMapper.add(user);
    return user;
  }

  @Override
  public long delete(long id) {
    return userMapper.delete(id);
  }

  @Override
  public long update(User user) {
    return userMapper.update(user);
  }
}