package com.memoblend.infrastructure.translator;

import org.springframework.beans.BeanUtils;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.auth.Auth;
import com.memoblend.applicationcore.auth.UserRole;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.infrastructure.mybatis.generated.entity.AppUserEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.AuthEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.DiaryEntity;
import com.memoblend.infrastructure.mybatis.generated.entity.UserRoleEntity;

/**
 * エンティティとドメインオブジェクトの変換を行うクラスです。
 */
public class EntityTranslator {

  /**
   * {@link UserRoleEntity}を{@link UserRole}に変換します。
   * 
   * @param entity 変換対象の{@link UserRoleEntity}
   * @return 変換後の{@link UserRole}
   */
  public static UserRole UserRoleEntityToUserRole(UserRoleEntity entity) {
    UserRole role = new UserRole();
    BeanUtils.copyProperties(entity, role);
    return role;
  }

  /**
   * {@link UserRole}を{@link UserRoleEntity}に変換します。
   * 
   * @param role 変換対象の{@link UserRole}
   * @return 変換後の{@link UserRoleEntity}
   */
  public static UserRoleEntity UserRoleToUserRoleEntity(UserRole role) {
    UserRoleEntity entity = new UserRoleEntity();
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
   * {@link AppUserEntity}を{@link AppUser}に変換します。
   * 
   * @param entity 変換対象の{@link AppUserEntity}
   * @return 変換後の{@link AppUser}
   */
  public static AppUser AppUserEntityToAppUser(AppUserEntity entity) {
    AppUser user = new AppUser();
    BeanUtils.copyProperties(entity, user);
    return user;
  }

  /**
   * {@link AppUser}を{@link AppUserEntity}に変換します。
   * 
   * @param user 変換対象の{@link AppUser}
   * @return 変換後の{@link AppUserEntity}
   */
  public static AppUserEntity AppUserToAppUserEntity(AppUser user) {
    AppUserEntity entity = new AppUserEntity();
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
