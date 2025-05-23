package com.memoblend.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.memoblend.web.filter.DummyUserInjectionFilter;
import java.util.Arrays;
import java.util.List;

/**
 * セキュリティ関連の実行クラスです。
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

  @Value("${cors.allowed.origins:}")
  private String allowedOrigins;

  /**
   * CORS 設定、認可機能を設定します。
   * 
   * @param http                     認証認可の設定クラス。
   * @param dummyUserInjectionFilter ダミーユーザ注入フィルタ（開発環境用、任意）。
   * @return フィルターチェーン。
   * @throws Exception 例外。
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      @Autowired(required = false) DummyUserInjectionFilter dummyUserInjectionFilter) throws Exception {
    http
        .securityMatcher("/api/**")
        .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
        .cors(cors -> cors.configurationSource(request -> createCorsConfiguration()))
        .anonymous(anon -> anon.disable());

    if (dummyUserInjectionFilter != null) {
      http.addFilterBefore(dummyUserInjectionFilter, UsernamePasswordAuthenticationFilter.class);
    }
    return http.build();
  }

  /**
   * CORSの設定を生成します。
   */
  private CorsConfiguration createCorsConfiguration() {
    CorsConfiguration conf = new CorsConfiguration();
    conf.setAllowCredentials(true);
    conf.setAllowedOrigins(Arrays.asList(allowedOrigins));
    conf.setAllowedMethods(List.of("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
    conf.setAllowedHeaders(List.of("*"));
    return conf;
  }
}