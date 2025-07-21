package com.memoblend.web.controller;

import com.memoblend.applicationcore.applicationservice.AuthApplicationService;
import com.memoblend.web.controller.dto.auth.LoginRequest;
import com.memoblend.web.controller.dto.auth.LoginResponse;
import com.memoblend.web.controller.dto.auth.TokenValidationRequest;
import com.memoblend.web.controller.dto.auth.TokenValidationResponse;
import com.memoblend.web.security.JwtTokenUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 認証関連のコントローラークラスです。
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Auth", description = "認証関連の API です。")
public class AuthController {

  private final JwtTokenUtil jwtTokenUtil;
  private final AuthApplicationService authApplicationService;

  /**
   * ログイン処理を行い、JWTトークンを生成します。
   * 
   * @param request ログインリクエスト。
   * @return ログイン成功時にJWTトークンを含むレスポンス。
   *         認証失敗時は400 Bad Requestを返します。
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    UserDetails userDetails = authApplicationService.authenticate(request.getAuthId(),
        request.getPassword());
    if (userDetails == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .contentType(MediaType.APPLICATION_PROBLEM_JSON)
          .body("ユーザーが見つかりません");
    }
    String jwt = jwtTokenUtil.generateToken(userDetails);
    LoginResponse response = new LoginResponse(jwt, "Bearer", userDetails.getUsername(), userDetails.getAuthorities());
    return ResponseEntity.ok(response);
  }

  /**
   * トークンの有効性検証を行います。
   * 
   * @param request 検証リクエスト。
   * @return 検証結果。
   */
  @PostMapping("/validate")
  public ResponseEntity<?> validate(@RequestBody TokenValidationRequest request) {
    boolean isValid = false;
    if (jwtTokenUtil.isTokenValid(request.getToken())) {
      isValid = true;
    }
    return ResponseEntity.ok(new TokenValidationResponse(isValid));
  }
}
