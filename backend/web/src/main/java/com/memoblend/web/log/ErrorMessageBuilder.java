package com.memoblend.web.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import org.springframework.context.MessageSource;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import com.memoblend.systemcommon.util.ApplicationContextWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ログでエラーメッセージを作成するためのクラスです。
 */
@Getter
@AllArgsConstructor
public class ErrorMessageBuilder {

  private static final MessageSource MESSAGE_SOURCE = ApplicationContextWrapper.getBean(MessageSource.class);

  private final Exception ex;
  private final String exceptionId;
  private final String[] frontMessageValue;
  private final String[] logMessageValue;

  /**
   * ProblemDetails の detail 情報に格納するスタックトレースを作成します。
   * 
   * @return スタックトレース。
   */
  public String createLogMessageStackTrace() {
    StringBuilder builder = new StringBuilder();
    String exceptionMessage = MESSAGE_SOURCE.getMessage(exceptionId, logMessageValue, Locale.getDefault());
    builder.append(exceptionId).append(" ").append(exceptionMessage).append(SystemPropertyConstants.LINE_SEPARATOR);
    StringWriter writer = new StringWriter();
    ex.printStackTrace(new PrintWriter(writer));
    builder.append(writer.getBuffer().toString());
    return builder.toString();
  }
}
