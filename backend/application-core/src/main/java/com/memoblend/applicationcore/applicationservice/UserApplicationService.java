package com.memoblend.applicationcore.applicationservice;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.memoblend.applicationcore.constant.MessageIdConstants;
import com.memoblend.applicationcore.user.User;
import com.memoblend.applicationcore.user.UserDomainService;
import com.memoblend.applicationcore.user.UserNotFoundException;
import com.memoblend.applicationcore.user.UserRepository;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import lombok.AllArgsConstructor;

/**
 * ユーザーのアプリケーションサービスです。
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserApplicationService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserDomainService userDomainService;
  private MessageSource messages;
  private final Logger apLog = Logger.getLogger(SystemPropertyConstants.APPLICATION_LOGGER);

  /**
   * 全てのユーザーをリストで取得します。
   * 
   * @return 全てのユーザーのリスト。
   */
  public List<User> getUsers() {
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_GET_USERS, new Object[] {}, Locale.getDefault()));
    return userRepository.findAll();
  }

  /**
   * ID を指定して、ユーザーを取得します。
   * 
   * @param id ユーザーの ID 。
   * @return 条件に合うユーザー。
   * @throws UserNotFoundException ユーザーが見つからない場合。
   */
  public User getUser(long id) throws UserNotFoundException {
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_GET_USER,
        new Object[] { id }, Locale.getDefault()));
    User user = userRepository.findById(id);
    if (user == null) {
      throw new UserNotFoundException(id);
    }
    return user;
  }

  /**
   * ユーザーを追加します。
   * 
   * @param user 追加するユーザー。
   * @return 追加されたユーザー。
   */
  public User addUser(User user) {
    User addedUser = userRepository.add(user);
    long addedId = addedUser.getId();
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_ADD_USER,
        new Object[] { addedId }, Locale.getDefault()));
    return addedUser;
  }

  /**
   * 既存のユーザーのデータを更新します。
   * 
   * @param user 更新するユーザー。
   * @throws UserNotFoundException ユーザーが見つからない場合。
   */
  public void updateUser(User user) throws UserNotFoundException {
    final long id = user.getId();
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_UPDATE_USER,
        new Object[] { id }, Locale.getDefault()));
    if (!userDomainService.isExistUser(id)) {
      throw new UserNotFoundException(id);
    }
    userRepository.update(user);
  }

  /**
   * ID を指定して、ユーザーを削除します。
   * 
   * @param id ユーザーの ID 。
   * @throws UserNotFoundException ユーザーが見つからない場合。
   */
  public void deleteUser(long id) throws UserNotFoundException {
    apLog.info(messages.getMessage(MessageIdConstants.D_USER_DELETE_USER,
        new Object[] { id }, Locale.getDefault()));
    if (!userDomainService.isExistUser(id)) {
      throw new UserNotFoundException(id);
    }
    userRepository.delete(id);
  }
}
