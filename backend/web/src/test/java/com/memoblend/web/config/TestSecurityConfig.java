package com.memoblend.web.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * テスト用のセキュリティ設定。
 * テスト時はセキュリティを無効にする。
 */
@TestConfiguration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("test")
public class TestSecurityConfig {

  /**
   * テスト用のセキュリティフィルターチェーンを定義します。
   * すべてのリクエストを許可します。
   *
   * @param http HttpSecurityオブジェクト。
   * @return SecurityFilterChainオブジェクト。
   * @throws Exception 例外。
   */
  @Bean
  public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/api/**")
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz.anyRequest().permitAll());

    return http.build();
  }

  /**
   * テスト用のパスワードエンコーダーの Bean 定義です。
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
