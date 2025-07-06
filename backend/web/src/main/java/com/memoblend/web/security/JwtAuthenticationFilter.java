package com.memoblend.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.memoblend.applicationcore.applicationservice.AuthenticationApplicationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;

/**
 * JWT認証フィルタークラスです。
 */
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationApplicationService authenticationApplicationService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (ExpiredJwtException e) {
        logger.warn("JWT Token が期限切れです: " + e.getMessage());
      } catch (SignatureException e) {
        logger.warn("JWT Token の署名が無効です: " + e.getMessage());
      } catch (MalformedJwtException e) {
        logger.warn("JWT Token の形式が不正です: " + e.getMessage());
      } catch (Exception e) {
        logger.warn("JWT Token の解析に失敗しました: " + e.getMessage());
      }
    } else {
      logger.debug("JWT Token が Bearer で始まっていないか、存在しません");
    }

    // トークンが有効で、セキュリティコンテキストが未設定の場合
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      try {
        UserDetails userDetails = this.authenticationApplicationService.loadUserByUsername(username);

        if (userDetails != null && jwtTokenUtil.isTokenValid(jwtToken, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      } catch (UsernameNotFoundException e) {
        logger.warn("ユーザーが見つかりません: " + username);
      }
    }
    chain.doFilter(request, response);
  }
}
