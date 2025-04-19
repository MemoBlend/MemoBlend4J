package com.memoblend.web.filter;

import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.memoblend.applicationcore.constant.UserRoleConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ダミーユーザーを SecurityContextHolder に詰めるためのフィルタークラスです。
 * 
 * <p>
 * 開発環境においてユーザ名が admin@example.com 、権限が管理者のユーザでアクセスしたことにして認証プロセスをスキップするために使用します。
 * また、本フィルターは WebSecurityConfig にて、セキュリティフィルターチェーンの
 * UsernamePasswordAuthenticationFilter の前に挿入します。
 * </p>
 */
@Profile("local")
@Component
public class DummyUserInjectionFilter extends OncePerRequestFilter {

  private static final String DUMMY_USERNAME = "admin@example.com";
  private static final String DUMMY_PASSWORD = "";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    UserDetails dummyUser = createDummyUser();
    setAuthentication(dummyUser);
    filterChain.doFilter(request, response);
  }

  private UserDetails createDummyUser() {
    return new User(DUMMY_USERNAME, DUMMY_PASSWORD,
        List.of(new SimpleGrantedAuthority(UserRoleConstants.USER)));
  }

  private void setAuthentication(UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
        userDetails.getPassword(), userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}