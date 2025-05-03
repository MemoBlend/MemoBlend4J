package com.memoblend.web.auth;

import java.util.ArrayList;
import java.util.List;
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
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return authentication.getName();
    }
    return "";
  }

  @Override
  public List<String> getLoginUserRoles() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }
    return new ArrayList<>();
  }

  @Override
  public boolean isInRole(String role) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .anyMatch(roles -> roles.equals(role));
    }
    return false;
  }
}
