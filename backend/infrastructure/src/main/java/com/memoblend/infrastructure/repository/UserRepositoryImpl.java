package com.memoblend.infrastructure.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserRepository;
import com.memoblend.infrastructure.mybatis.generated.entity.UserEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.UserEntityExample;
import com.memoblend.infrastructure.mybatis.generated.mapper.UserMapper;
import com.memoblend.infrastructure.translator.EntityTranslator;

import lombok.AllArgsConstructor;

/**
 * ユーザーのリポジトリ実装クラスです。
 */
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements AppUserRepository {

  private final UserMapper userMapper;

  @Override
  public List<AppUser> findAll() {
    UserEntityExample example = new UserEntityExample();
    List<UserEntity> entities = userMapper.selectByExample(example);
    List<AppUser> users = entities.stream().map(EntityTranslator::UserEntityToUser).toList();
    return users;
  }

  @Override
  public AppUser findById(long id) {
    UserEntity entity = userMapper.selectByPrimaryKey(id);
    return EntityTranslator.UserEntityToUser(entity);
  }

  @Override
  public List<AppUser> findByAuthId(String authId) {
    UserEntityExample example = new UserEntityExample();
    example.createCriteria().andAuthIdEqualTo(authId);
    List<UserEntity> entities = userMapper.selectByExample(example);
    List<AppUser> users = entities.stream()
        .map(EntityTranslator::UserEntityToUser)
        .toList();
    return users;
  }

  @Override
  public AppUser add(AppUser user) {
    UserEntity entity = EntityTranslator.UserToUserEntity(user);
    userMapper.insert(entity);
    user.setId(entity.getId());
    return user;
  }

  @Override
  public long delete(long id) {
    return userMapper.deleteByPrimaryKey(id);
  }

  @Override
  public long update(AppUser user) {
    UserEntity entity = EntityTranslator.UserToUserEntity(user);
    return userMapper.updateByPrimaryKey(entity);
  }
}