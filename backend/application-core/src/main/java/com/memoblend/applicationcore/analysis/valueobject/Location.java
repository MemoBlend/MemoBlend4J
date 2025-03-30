package com.memoblend.applicationcore.analysis.valueobject;

import com.memoblend.applicationcore.analysis.ScheduleValidationException;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * スケジュールの場所を表す値オブジェクトです。
 */
@Getter
@EqualsAndHashCode
public class Location {
  private final String value;

  /**
   * {@link Location} クラスの新しいインスタンスを初期化します。
   *
   * @param value 場所の値。
   * @throws ScheduleValidationException 場所が不正な場合。
   */
  public Location(String value) throws ScheduleValidationException {
    if (value == null || value.isEmpty() || value.isBlank()) {
      throw new ScheduleValidationException(
          ExceptionIdConstants.E_SCHEDULE_FIELD_IS_REQUIRED,
          new String[] { String.valueOf("場所") },
          new String[] { String.valueOf("場所") });
    }
    if (value.length() <= 1 || value.length() >= 20) {
      throw new ScheduleValidationException(
          ExceptionIdConstants.E_SCHEDULE_VALUE_IS_OUT_OF_RANGE,
          new String[] { String.valueOf("場所"), "1", "20" },
          new String[] { String.valueOf("場所"), "1", "20" });
    }
    this.value = value;
  }
}
