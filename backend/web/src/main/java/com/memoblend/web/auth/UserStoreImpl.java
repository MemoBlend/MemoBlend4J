package com.memoblend.web.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.memoblend.applicationcore.auth.UserStore;

/**
 * ログインしているユーザーの情報を保持するためのクラスです。
 */
@Component
public class UserStoreImpl implements UserStore {

  @Override
  public String getLoginUserName() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(Authentication::getName)
        .orElse("");
  }

  @Override
  public List<String> getLoginUserRoles() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(auth -> auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
        .orElseGet(ArrayList::new);
  }

  @Override
  public boolean isInRole(String role) {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(auth -> auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
            .anyMatch(roles -> roles.equals(role)))
        .orElse(false);
  }
}
