package com.memoblend.applicationcore.applicationservice;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  /**
   * ユーザー名でユーザー情報を取得します。
   * 
   * @param username ユーザー名。
   * @return UserDetails ユーザーの詳細情報。
   * @throws UsernameNotFoundException ユーザー名に対応するユーザーが見つからない場合にスローされます。
   */
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if ("admin".equals(username)) {
      return User.builder()
          .username("admin")
          .password(passwordEncoder.encode("password"))
          .authorities("ROLE_ADMIN")
          .build();
    } else if ("user".equals(username)) {
      return User.builder()
          .username("user")
          .password(passwordEncoder.encode("password"))
          .authorities("ROLE_USER")
          .build();
    }
    throw new UsernameNotFoundException("ユーザー名：" + username + "のユーザーが見つかりません。");
  }
}
