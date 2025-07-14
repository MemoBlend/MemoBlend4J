package com.memoblend.web.controller;

import com.memoblend.applicationcore.applicationservice.AuthenticationApplicationService;
import com.memoblend.applicationcore.applicationservice.UserApplicationService;
import com.memoblend.applicationcore.user.User;
import com.memoblend.applicationcore.user.UserNotFoundException;
import com.memoblend.systemcommon.constant.CommonExceptionIdConstants;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import com.memoblend.web.controller.dto.auth.LoginRequest;
import com.memoblend.web.controller.dto.auth.LoginResponse;
import com.memoblend.web.controller.dto.auth.TokenValidationRequest;
import com.memoblend.web.controller.dto.auth.TokenValidationResponse;
import com.memoblend.web.controller.util.ProblemDetailsFactory;
import com.memoblend.web.log.ErrorMessageBuilder;
import com.memoblend.web.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
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
public class AuthController {

  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationApplicationService authenticationApplicationService;
  private final UserApplicationService userApplicationService;
  private final ProblemDetailsFactory problemDetailsFactory;
  private static final Logger apLog = LoggerFactory.getLogger(SystemPropertyConstants.APPLICATION_LOGGER);

  /**
   * ログイン処理を行い、JWTトークンを生成します。
   * 
   * @param request ログインリクエスト。
   * @return ログイン成功時にJWTトークンを含むレスポンス。
   *         認証失敗時は400 Bad Requestを返します。
   */

  @Operation(summary = "ログイン処理を行い、JWTトークンを生成します。", description = "ログイン処理を行い、JWTトークンを生成します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "成功。", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))),
      @ApiResponse(responseCode = "400", description = "認証失敗。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "ユーザーが見つかりません。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    UserDetails userDetails = authenticationApplicationService.authenticate(request.getAuthId(),
        request.getPassword());
    if (userDetails == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .contentType(MediaType.APPLICATION_PROBLEM_JSON)
          .body("ユーザーが見つかりません");
    }
    String name = "";
    try {
      User user = userApplicationService.getUser(request.getAuthId());
      name = user.getName();
    } catch (UserNotFoundException e) {
      apLog.info(e.getMessage());
      apLog.debug(ExceptionUtils.getStackTrace(e));
      ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, e.getExceptionId(),
          e.getLogMessageValue(), e.getFrontMessageValue());
      ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
          CommonExceptionIdConstants.E_BUSINESS, HttpStatus.NOT_FOUND);
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .contentType(MediaType.APPLICATION_PROBLEM_JSON)
          .body(problemDetail);
    }
    String jwt = jwtTokenUtil.generateToken(userDetails);
    LoginResponse response = new LoginResponse(jwt, "Bearer", name, userDetails.getAuthorities());
    return ResponseEntity.ok(response);
  }

  /**
   * トークンの有効性検証を行います。
   * 
   * @param request 検証リクエスト。
   * @return 検証結果。
   */
  @Operation(summary = "トークンの有効性検証を行います。", description = "トークンの有効性検証を行います。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "トークンが有効です。", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenValidationResponse.class))),
      @ApiResponse(responseCode = "400", description = "トークンが無効です。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
  })
  @PostMapping("/validate")
  public ResponseEntity<?> validate(@RequestBody TokenValidationRequest request) {
    boolean isValid = false;
    String username = null;
    if (jwtTokenUtil.isTokenValid(request.getToken())) {
      isValid = true;
      username = jwtTokenUtil.getUsernameFromToken(request.getToken());
    }
    return ResponseEntity.ok(new TokenValidationResponse(isValid, username));
  }
}
