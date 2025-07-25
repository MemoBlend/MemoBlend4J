package com.memoblend.infrastructure.translator;

import org.springframework.beans.BeanUtils;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.auth.Auth;
import com.memoblend.applicationcore.auth.Role;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.infrastructure.mybatis.generated.entity.AuthEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.DiaryEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.RoleEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.UserEntity;

/**
 * エンティティとドメインオブジェクトの変換を行うクラスです。
 */
public class EntityTranslator {

  /**
   * {@link RoleEntity}を{@link Role}に変換します。
   * 
   * @param entity 変換対象の{@link RoleEntity}
   * @return 変換後の{@link Role}
   */
  public static Role RoleEntityToRole(RoleEntity entity) {
    Role role = new Role();
    BeanUtils.copyProperties(entity, role);
    return role;
  }

  /**
   * {@link Role}を{@link RoleEntity}に変換します。
   * 
   * @param role 変換対象の{@link Role}
   * @return 変換後の{@link RoleEntity}
   */
  public static RoleEntity RoleToRoleEntity(Role role) {
    RoleEntity entity = new RoleEntity();
    BeanUtils.copyProperties(role, entity);
    return entity;
  }

  /**
   * {@link AuthEntity}を{@link Auth}に変換します。
   * 
   * @param entity 変換対象の{@link AuthEntity}
   * @return 変換後の{@link Auth}
   */
  public static Auth AuthEntityToAuth(AuthEntity entity) {
    Auth auth = new Auth();
    BeanUtils.copyProperties(entity, auth);
    return auth;
  }

  /**
   * {@link Auth}を{@link AuthEntity}に変換します。
   * 
   * @param auth 変換対象の{@link Auth}
   * @return 変換後の{@link AuthEntity}
   */
  public static AuthEntity AuthToAuthEntity(Auth auth) {
    AuthEntity entity = new AuthEntity();
    BeanUtils.copyProperties(auth, entity);
    return entity;
  }

  /**
   * {@link UserEntity}を{@link AppUser}に変換します。
   * 
   * @param entity 変換対象の{@link UserEntity}
   * @return 変換後の{@link AppUser}
   */
  public static AppUser UserEntityToUser(UserEntity entity) {
    AppUser user = new AppUser();
    BeanUtils.copyProperties(entity, user);
    return user;
  }

  /**
   * {@link AppUser}を{@link UserEntity}に変換します。
   * 
   * @param user 変換対象の{@link AppUser}
   * @return 変換後の{@link UserEntity}
   */
  public static UserEntity UserToUserEntity(AppUser user) {
    UserEntity entity = new UserEntity();
    BeanUtils.copyProperties(user, entity);
    return entity;
  }

  /**
   * {@link DiaryEntity}を{@link Diary}に変換します。
   * 
   * @param entity 変換対象の{@link DiaryEntity}
   * @return 変換後の{@link Diary}
   */
  public static Diary DiaryEntityToDiary(DiaryEntity entity) {
    Diary diary = new Diary();
    BeanUtils.copyProperties(entity, diary);
    return diary;
  }

  /**
   * {@link Diary}を{@link DiaryEntity}に変換します。
   * 
   * @param diary 変換対象の{@link Diary}
   * @return 変換後の{@link DiaryEntity}
   */
  public static DiaryEntity DiaryToDiaryEntity(Diary diary) {
    DiaryEntity entity = new DiaryEntity();
    BeanUtils.copyProperties(diary, entity);
    return entity;
  }
}
