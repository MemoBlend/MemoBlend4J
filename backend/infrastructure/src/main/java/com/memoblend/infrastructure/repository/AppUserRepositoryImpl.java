package com.memoblend.infrastructure.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserRepository;
import com.memoblend.infrastructure.mybatis.generated.entity.AppUserEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.AppUserEntityExample;
import com.memoblend.infrastructure.mybatis.generated.mapper.AppUserMapper;
import com.memoblend.infrastructure.translator.EntityTranslator;
import lombok.AllArgsConstructor;

/**
 * ユーザーのリポジトリ実装クラスです。
 */
@Repository
@AllArgsConstructor
public class AppUserRepositoryImpl implements AppUserRepository {

  private final AppUserMapper userMapper;

  @Override
  public List<AppUser> findAll() {
    AppUserEntityExample example = new AppUserEntityExample();
    List<AppUserEntity> entities = userMapper.selectByExample(example);
    List<AppUser> users = entities.stream().map(EntityTranslator::AppUserEntityToAppUser).toList();
    return users;
  }

  @Override
  public AppUser findById(long id) {
    AppUserEntity entity = userMapper.selectByPrimaryKey(id);
    return EntityTranslator.AppUserEntityToAppUser(entity);
  }

  @Override
  public List<AppUser> findByAuthId(String authId) {
    AppUserEntityExample example = new AppUserEntityExample();
    example.createCriteria().andAuthIdEqualTo(authId);
    List<AppUserEntity> entities = userMapper.selectByExample(example);
    List<AppUser> users = entities.stream()
        .map(EntityTranslator::AppUserEntityToAppUser)
        .toList();
    return users;
  }

  @Override
  public AppUser add(AppUser user) {
    AppUserEntity entity = EntityTranslator.AppUserToAppUserEntity(user);
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
    AppUserEntity entity = EntityTranslator.AppUserToAppUserEntity(user);
    return userMapper.updateByPrimaryKey(entity);
  }
}