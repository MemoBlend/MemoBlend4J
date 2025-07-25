package com.memoblend.applicationcore.applicationservice;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserDomainService;
import com.memoblend.applicationcore.appuser.AppUserNotFoundException;
import com.memoblend.applicationcore.appuser.AppUserRepository;
import com.memoblend.applicationcore.constant.MessageIdConstants;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import lombok.AllArgsConstructor;

/**
 * ユーザーのアプリケーションサービスです。
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AppUserApplicationService {

  private final AppUserRepository userRepository;
  private final AppUserDomainService userDomainService;
  private final MessageSource messages;

  private final Logger apLog = Logger.getLogger(SystemPropertyConstants.APPLICATION_LOGGER);

  /**
   * 全てのユーザーをリストで取得します。
   * 
   * @return 全てのユーザーのリスト。
   */
  public List<AppUser> getUsers() {
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_GET_USERS, new Object[] {}, Locale.getDefault()));
    return userRepository.findAll();
  }

  /**
   * ID を指定して、ユーザーを取得します。
   * 
   * @param id ユーザーの ID 。
   * @return 条件に合うユーザー。
   * @throws AppUserNotFoundException ユーザーが見つからない場合。
   */
  public AppUser getUser(long id) throws AppUserNotFoundException {
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_GET_USER, new Object[] { id }, Locale.getDefault()));
    AppUser user = userRepository.findById(id);
    if (user == null) {
      throw new AppUserNotFoundException(id);
    }
    return user;
  }

  /**
   * 認証 ID を指定して、ユーザーを取得します。
   *
   * @param authId 認証 ID 。
   * @return 条件に合うユーザー。
   * @throws AppUserNotFoundException ユーザーが見つからない場合。
   */
  public List<AppUser> getUserByAuthId(String authId) throws AppUserNotFoundException {
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_GET_USER_BY_AUTH_ID, new Object[] { authId },
        Locale.getDefault()));
    List<AppUser> users = userRepository.findByAuthId(authId);
    if (users.isEmpty()) {
      throw new AppUserNotFoundException(authId);
    }
    return users;
  }

  /**
   * ユーザーを追加します。
   * 
   * @param user 追加するユーザー。
   * @return 追加されたユーザー。
   */
  public AppUser addUser(AppUser user) {
    AppUser addedUser = userRepository.add(user);
    long addedId = addedUser.getId();
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_ADD_USER, new Object[] { addedId }, Locale.getDefault()));
    return addedUser;
  }

  /**
   * 既存のユーザーのデータを更新します。
   * 
   * @param user 更新するユーザー。
   * @throws AppUserNotFoundException ユーザーが見つからない場合。
   */
  public void updateUser(AppUser user) throws AppUserNotFoundException {
    final long id = user.getId();
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_UPDATE_USER, new Object[] { id }, Locale.getDefault()));
    if (!userDomainService.isExistUser(id)) {
      throw new AppUserNotFoundException(id);
    }
    userRepository.update(user);
  }

  /**
   * ID を指定して、ユーザーを削除します。
   * 
   * @param id ユーザーの ID 。
   * @throws AppUserNotFoundException ユーザーが見つからない場合。
   */
  public void deleteUser(long id) throws AppUserNotFoundException {
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_DELETE_USER, new Object[] { id }, Locale.getDefault()));
    if (!userDomainService.isExistUser(id)) {
      throw new AppUserNotFoundException(id);
    }
    userRepository.delete(id);
  }
}
