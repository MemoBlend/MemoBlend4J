package com.memoblend.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.memoblend.applicationcore.applicationservice.AuthenticationApplicationService;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.List;

/**
 * セキュリティ関連の実行クラスです。
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("!test")
@RequiredArgsConstructor
public class WebSecurityConfig {

  @Value("${cors.allowed.origins:}")
  private String allowedOrigins;

  private final JwtProperties jwtProperties;
  private final Environment environment;

  /**
   * CORS 設定、認可機能を設定します。
   * 
   * @param http 認証認可の設定クラス。
   * @return フィルターチェーン。
   * @throws Exception 例外。
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationApplicationService authenticationApplicationService)
      throws Exception {

    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtProperties);
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
        jwtTokenUtil, authenticationApplicationService);

    http
        .securityMatcher("/api/**")
        .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
        .cors(cors -> cors.configurationSource(request -> createCorsConfiguration()))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/auth/**", "/api-docs/**", "/swagger-ui/**", "/h2-console/**").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .anonymous(anon -> anon.disable());
    if (environment.acceptsProfiles(Profiles.of("local"))) {
      http.addFilterBefore(new DummyUserInjectionFilter(), UsernamePasswordAuthenticationFilter.class);
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
    conf.setMaxAge(3600L);
    return conf;
  }

  /**
   * パスワードエンコーダーの Bean 定義です。
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}