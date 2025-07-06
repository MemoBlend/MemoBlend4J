package com.memoblend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * セキュアなサンプルAPIコントローラークラスです。
 */
@RestController
@RequestMapping("/api/secure")
public class SecureController {

  /**
   * 認証されたユーザーのみアクセス可能なエンドポイント。
   */
  @GetMapping("/user")
  public ResponseEntity<?> getUserInfo(Authentication authentication) {
    Map<String, Object> response = new HashMap<>();
    response.put("username", authentication.getName());
    response.put("authorities", authentication.getAuthorities());
    response.put("message", "認証されたユーザー情報");
    return ResponseEntity.ok(response);
  }

  /**
   * ADMIN権限を持つユーザーのみアクセス可能なエンドポイント。
   */
  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAdminInfo() {
    Map<String, Object> response = new HashMap<>();
    response.put("message", "管理者専用情報");
    response.put("data", "これは管理者のみが見ることができるデータです");
    return ResponseEntity.ok(response);
  }

  /**
   * 一般ユーザー権限以上でアクセス可能なエンドポイント。
   */
  @GetMapping("/data")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<?> getData() {
    Map<String, Object> response = new HashMap<>();
    response.put("message", "ユーザーデータ");
    response.put("data", "これは認証されたユーザーが見ることができるデータです");
    return ResponseEntity.ok(response);
  }
}
