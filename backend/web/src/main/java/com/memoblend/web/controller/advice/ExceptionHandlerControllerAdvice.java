package com.memoblend.web.controller.advice;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.memoblend.applicationcore.api.ExternalApiException;
import com.memoblend.applicationcore.auth.PermissionDeniedException;
import com.memoblend.systemcommon.constant.CommonExceptionIdConstants;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import com.memoblend.systemcommon.exception.LogicException;
import com.memoblend.systemcommon.exception.SystemException;
import com.memoblend.systemcommon.exception.ValidationException;
import com.memoblend.web.controller.util.ProblemDetailsFactory;
import com.memoblend.web.log.ErrorMessageBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

/**
 * 本番環境で使用する集約例外ハンドラーです。
 */
@ControllerAdvice(basePackages = "com.memoblend")
@AllArgsConstructor
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

  private static final Logger apLog = LoggerFactory.getLogger(SystemPropertyConstants.APPLICATION_LOGGER);

  private final ProblemDetailsFactory problemDetailsFactory;

  /**
   * バリデーションエラーをステータスコード 400 で返却します。
   * 
   * @param e バリデーションエラー。
   * @return ステータスコード 400 のレスポンス。
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ProblemDetail> handleValidationException(ValidationException e) {
    apLog.info(e.getMessage());
    apLog.debug(ExceptionUtils.getStackTrace(e));
    ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(
        e, e.getExceptionId(), e.getFrontMessageValue(), e.getLogMessageValue());
    ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
        CommonExceptionIdConstants.E_BUSINESS, HttpStatus.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problemDetail);
  }

  /**
   * 権限エラーをステータスコード 404 で返却します。
   * 
   * @param e 権限エラー。
   * @return ステータスコード 404 のレスポンス。
   */
  @ExceptionHandler(PermissionDeniedException.class)
  public ResponseEntity<ProblemDetail> handlePermissionDeniedException(PermissionDeniedException e) {
    apLog.info(e.getMessage());
    apLog.debug(ExceptionUtils.getStackTrace(e));
    ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, e.getExceptionId(),
        e.getFrontMessageValue(),
        e.getLogMessageValue());
    ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
        CommonExceptionIdConstants.E_BUSINESS, HttpStatus.NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problemDetail);
  }

  /**
   * 外部APIの呼び出しに失敗した場合の例外をステータスコード 500 で返却します。
   *
   * @param e 外部APIの呼び出しに失敗した例外。
   * @return ステータスコード 500 のレスポンス。
   */
  @ExceptionHandler(ExternalApiException.class)
  public ResponseEntity<ProblemDetail> handleExternalApiException(ExternalApiException e) {
    apLog.error(e.getMessage());
    apLog.debug(ExceptionUtils.getStackTrace(e));
    ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, e.getExceptionId(),
        e.getFrontMessageValue(),
        e.getLogMessageValue());
    ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
        CommonExceptionIdConstants.E_BUSINESS, HttpStatus.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problemDetail);
  }

  /**
   * その他の業務エラーをステータースコード 500 で返却します。
   *
   * @param e   業務例外。
   * @param req リクエスト。
   * @return ステータースコード 500 のレスポンス。
   */
  @ExceptionHandler(LogicException.class)
  public ResponseEntity<ProblemDetail> handleLogicException(LogicException e, HttpServletRequest req) {
    ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, CommonExceptionIdConstants.E_BUSINESS,
        null,
        null);
    apLog.error(errorBuilder.createLogMessageStackTrace());
    ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(
        errorBuilder,
        CommonExceptionIdConstants.E_BUSINESS,
        HttpStatus.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problemDetail);
  }

  /**
   * その他のシステムエラーをステータースコード 500 で返却します。
   *
   * @param e   その他の例外。
   * @param req リクエスト。
   * @return ステータースコード 500 のレスポンス。
   */
  @ExceptionHandler(SystemException.class)
  public ResponseEntity<ProblemDetail> handleSystemException(SystemException e, HttpServletRequest req) {
    ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, CommonExceptionIdConstants.E_SYSTEM, null,
        null);
    apLog.error(errorBuilder.createLogMessageStackTrace());
    ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(
        errorBuilder,
        CommonExceptionIdConstants.E_SYSTEM,
        HttpStatus.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problemDetail);
  }

  /**
   * 上記のいずれにも当てはまらない例外をステータースコード 500 で返却します。
   *
   * @param e   その他の例外。
   * @param req リクエスト。
   * @return ステータースコード 500 のレスポンス。
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetail> handleException(Exception e, HttpServletRequest req) {
    ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, CommonExceptionIdConstants.E_SYSTEM, null,
        null);
    apLog.error(errorBuilder.createLogMessageStackTrace());
    ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
        CommonExceptionIdConstants.E_SYSTEM,
        HttpStatus.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problemDetail);
  }

}
