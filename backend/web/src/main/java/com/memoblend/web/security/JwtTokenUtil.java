package com.memoblend.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Token管理用のユーティリティクラスです。
 * このクラスは、JWTトークンの生成、検証、およびクレームの取得を行います。
 */
@Component
public class JwtTokenUtil {

  private final JwtProperties jwtProperties;
  private final SecretKey key;

  public JwtTokenUtil(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
    this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
  }

  /**
   * トークンからユーザー名を取得します。
   */
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * トークンから発行日時を取得します。
   */
  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  /**
   * トークンから有効期限を取得します。
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * トークンから指定されたクレームを取得します。
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * UserDetailsからトークンを生成します。
   * ユーザーの権限をクレームとして含め、トークンを作成します。
   */
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", userDetails.getAuthorities());
    return createToken(claims, userDetails.getUsername());
  }

  /**
   * トークンが有効かどうかチェックします。
   * UserDetailsを使用して、トークンのユーザー名と有効期限を確認します。
   */
  public Boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * トークンが有効かどうかチェックします。（UserDetailsなし）
   */
  public Boolean isTokenValid(String token) {
    try {
      return !isTokenExpired(token);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * トークンを作成します。
   * クレームとサブジェクト（ユーザー名）を含め、
   * 発行日時と有効期限を設定してトークンを生成します。
   */
  private String createToken(Map<String, Object> claims, String subject) {
    Instant now = Instant.now();
    Instant expiration = now.plusMillis(jwtProperties.getExpiration());

    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiration))
        .signWith(key)
        .compact();
  }

  /**
   * トークンから全クレームを取得します。
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * トークンが有効期限切れかどうかチェックします。
   * 有効期限が現在の日時より前であれば、トークンは期限切れとみなされます。
   */
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

}