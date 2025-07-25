package com.memoblend.infrastructure.translator;

import java.sql.Timestamp;
import java.time.LocalDate;
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
  public static UserRole userRoleEntityToUserRole(UserRoleEntity entity) {
    if (entity == null) {
      return null;
    }
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
  public static UserRoleEntity userRoleToUserRoleEntity(UserRole role) {
    if (role == null) {
      return null;
    }
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
  public static Auth authEntityToAuth(AuthEntity entity) {
    if (entity == null) {
      return null;
    }
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
  public static AuthEntity authToAuthEntity(Auth auth) {
    if (auth == null) {
      return null;
    }
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
  public static AppUser appUserEntityToAppUser(AppUserEntity entity) {
    if (entity == null) {
      return null;
    }
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
  public static AppUserEntity appUserToAppUserEntity(AppUser user) {
    if (user == null) {
      return null;
    }
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
  public static Diary diaryEntityToDiary(DiaryEntity entity) {
    if (entity == null) {
      return null;
    }
    try {
      LocalDate createdDate = null;
      if (entity.getCreatedDate() != null) {
        createdDate = entity.getCreatedDate().toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();
      }
      return new Diary(
          entity.getId(),
          entity.getTitle(),
          entity.getContent(),
          createdDate,
          entity.getIsDeleted(),
          entity.getUserId());
    } catch (Exception e) {
      throw new RuntimeException("DiaryEntityからDiaryへの変換に失敗しました", e);
    }
  }

  /**
   * {@link Diary}を{@link DiaryEntity}に変換します。
   * 
   * @param diary 変換対象の{@link Diary}
   * @return 変換後の{@link DiaryEntity}
   */
  public static DiaryEntity diaryToDiaryEntity(Diary diary) {
    if (diary == null) {
      return null;
    }
    DiaryEntity entity = new DiaryEntity();
    entity.setId(diary.getId());
    entity.setTitle(diary.getTitle());
    entity.setContent(diary.getContent());
    if (diary.getCreatedDate() != null) {
      entity.setCreatedDate(Timestamp.valueOf(
          diary.getCreatedDate().atStartOfDay()));
    }
    entity.setIsDeleted(diary.getIsDeleted());
    entity.setUserId(diary.getUserId());
    return entity;
  }
}
