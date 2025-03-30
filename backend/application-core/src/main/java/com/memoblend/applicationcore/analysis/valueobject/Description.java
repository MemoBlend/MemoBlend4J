
package com.memoblend.applicationcore.analysis.valueobject;

import com.memoblend.applicationcore.analysis.ScheduleValidationException;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 詳細を表す値オブジェクトです。
 */
@Getter
@EqualsAndHashCode
public class Description {
  private final String value;

  /**
   * {@link Description} クラスの新しいインスタンスを初期化します。
   * 
   * @param value 詳細の値。
   * @throws ScheduleValidationException 詳細が不正な場合。
   */
  public Description(String value) throws ScheduleValidationException {
    if (value == null || value.isEmpty() || value.isBlank()) {
      throw new ScheduleValidationException(
          ExceptionIdConstants.E_SCHEDULE_FIELD_IS_REQUIRED,
          new String[] { String.valueOf("詳細") },
          new String[] { String.valueOf("詳細") });
    }
    if (value.length() <= 1 || value.length() >= 100) {
      throw new ScheduleValidationException(
          ExceptionIdConstants.E_SCHEDULE_VALUE_IS_OUT_OF_RANGE,
          new String[] { String.valueOf("詳細"), "1", "100" },
          new String[] { String.valueOf("詳細"), "1", "100" });
    }
    this.value = value;
  }
}
