package com.memoblend.applicationcore.appuser;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

/**
 * ユーザーのドメインサービスです。
 */
@AllArgsConstructor
@Component
public class AppUserDomainService {
  private final AppUserRepository userRepository;

  /**
   * ID を指定して、ユーザーが存在するかどうかを判定します。
   * 
   * @param id ユーザーの ID 。
   * @return ユーザーが存在する場合は true 、存在しない場合は false 。
   */
  public boolean isExistUser(long id) {
    AppUser user = userRepository.findById(id);
    return user != null && !user.getIsDeleted();
  }
}
