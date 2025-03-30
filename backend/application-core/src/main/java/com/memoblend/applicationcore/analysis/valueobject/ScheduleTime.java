package com.memoblend.applicationcore.analysis.valueobject;

import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * スケジュールの時間を表す値オブジェクトです。
 */
@Getter
@EqualsAndHashCode
public class ScheduleTime {
  private final ZonedDateTime value;

  /**
   * {@link ScheduleTime} クラスの新しいインスタンスを初期化します。
   *
   * @param value スケジュールの時間。
   */
  public ScheduleTime(ZonedDateTime value) {
    this.value = value;
  }
}
