package com.memoblend.applicationcore.applicationservice;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.memoblend.applicationcore.auth.Auth;
import com.memoblend.applicationcore.auth.AuthRepository;
import lombok.AllArgsConstructor;

/**
 * 認証関連のアプリケーションサービスクラスです。
 * ユーザーの認証やユーザーデータの取得を行います。
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AuthenticationApplicationService implements UserDetailsService {
  private final PasswordEncoder passwordEncoder;
  private final AuthRepository authRepository;

  /**
   * ユーザーの認証を行います。
   * 
   * @param username ユーザー名。
   * @param password パスワード。
   * @return 認証が成功した場合のUserDetails、失敗した場合またはUsernameNotFoundExceptionが発生した場合はnull。
   */
  public UserDetails authenticate(String username, String password) {
    try {
      UserDetails userDetails = loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        return null;
      }
      return userDetails;
    } catch (UsernameNotFoundException e) {
      return null;
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Auth auth = authRepository.findById(username);
    if (auth == null || auth.isDeleted()) {
      throw new UsernameNotFoundException("ユーザーID：" + username + "のユーザーが見つかりません。");
    }
    return User.builder()
        .username(auth.getId())
        .password(auth.getPassword())
        .authorities(auth.getUserRole())
        .build();
  }
}
