package com.memoblend.web.controller.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import com.memoblend.web.constant.WebConstants;
import com.memoblend.web.log.ErrorMessageBuilder;
import lombok.AllArgsConstructor;

/**
 * エラーレスポンスに含める ProblemDetails を作成するクラスです。
 */
@Component
@AllArgsConstructor
public class ProblemDetailsFactory {

  private final Environment env;
  private final MessageSource messages;

  /**
   * エラーレスポンスに含める ProblemDetails を作成します。
   *
   * @param errorBuilder 例外ビルダー。
   * @param titleId      タイトルのメッセージ ID 。
   * @param status       ステータスコード。
   * @return エラーレスポンスに格納する ProblemDetails 。
   */
  public ProblemDetail createProblemDetail(ErrorMessageBuilder errorBuilder, String titleId, HttpStatus status) {

    ProblemDetail problemDetail = ProblemDetail.forStatus(status);

    problemDetail.setTitle(messages.getMessage(titleId, new String[] {}, Locale.getDefault()));

    // 開発環境においては、 detail プロパティにスタックトレースを含める
    // 開発環境かどうかの判断は、環境変数の Profile をもとに判断する
    String[] activeProfiles = env.getActiveProfiles();
    if (activeProfiles.length == 0) {
      activeProfiles = env.getDefaultProfiles();
    }

    if (Arrays.stream(activeProfiles).anyMatch(profile -> Objects.equals(profile, "local"))) {
      problemDetail.setDetail(errorBuilder.createLogMessageStackTrace());
    }

    // 拡張メンバーとして exceptionId と exceptionValues を含める
    Map<String, Object> errorProperty = new LinkedHashMap<>();
    errorProperty.put(WebConstants.PROBLEM_DETAILS_EXCEPTION_ID, errorBuilder.getExceptionId());
    errorProperty.put(WebConstants.PROBLEM_DETAILS_EXCEPTION_VALUES, errorBuilder.getFrontMessageValue());

    problemDetail.setProperties(errorProperty);

    return problemDetail;
  }
}
