package com.memoblend.web.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * JWT設定のプロパティクラスです。
 */
@ConfigurationProperties(prefix = "jwt")
@Data
@Component
public class JwtProperties {
  private String secret;
  private long expiration;
  private String issuer;
}
