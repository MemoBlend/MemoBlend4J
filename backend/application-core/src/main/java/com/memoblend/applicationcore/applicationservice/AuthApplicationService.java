package com.memoblend.applicationcore.applicationservice;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.memoblend.applicationcore.appuser.AppUserNotFoundException;
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
public class AuthApplicationService {
  private final PasswordEncoder passwordEncoder;
  private final AuthRepository authRepository;

  /**
   * ユーザーの認証を行います。
   *
   * @param authId   認証ID。
   * @param password パスワード。
   * @return 認証が成功した場合のUserDetails、失敗した場合はnull。
   */
  public UserDetails authenticate(String authId, String password) {
    Auth auth = authRepository.findById(authId);
    if (auth == null) {
      return null;
    }
    UserDetails userDetails = User.builder()
        .username(auth.getId())
        .password(auth.getPasswordHash())
        .authorities(auth.getUserRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .toList())
        .build();

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      return null;
    }
    return userDetails;
  }

  /**
   * 認証 ID を使用してユーザーデータを取得します。
   * 
   * @param authId 認証 ID。
   * @throws AppUserNotFoundException 認証 ID に対応するユーザーが存在しない場合。
   */
  public UserDetails loadUserByAuthId(String authId) throws AppUserNotFoundException {
    Auth auth = authRepository.findById(authId);
    if (auth == null) {
      throw new AppUserNotFoundException(authId);
    }
    return User.builder()
        .username(auth.getId())
        .password(auth.getPasswordHash())
        .authorities(auth.getUserRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .toList())
        .build();
  }
}
