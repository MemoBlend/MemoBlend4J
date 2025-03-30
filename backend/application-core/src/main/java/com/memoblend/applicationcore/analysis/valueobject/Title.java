package com.memoblend.applicationcore.analysis.valueobject;

import com.memoblend.applicationcore.analysis.ScheduleValidationException;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * タイトルを表す値オブジェクトです。
 */
@Getter
@EqualsAndHashCode
public class Title {
  private final String value;

  /**
   * {@link Title} クラスの新しいインスタンスを初期化します。
   * 
   * @param value タイトルの値。
   * @throws ScheduleValidationException タイトルが不正な場合。
   */
  public Title(String value) throws ScheduleValidationException {
    if (value == null || value.isEmpty() || value.isBlank()) {
      throw new ScheduleValidationException(
          ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED,
          new String[] { String.valueOf("タイトル") },
          new String[] { String.valueOf("タイトル") });
    }
    if (value.length() <= 1 || value.length() >= 30) {
      throw new ScheduleValidationException(
          ExceptionIdConstants.E_DIARY_VALUE_IS_OUT_OF_RANGE,
          new String[] { String.valueOf("タイトル"), "1", "30" },
          new String[] { String.valueOf("タイトル"), "1", "30" });
    }
    this.value = value;
  }
}
