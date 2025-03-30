package com.memoblend.applicationcore.analysis;

import java.time.ZonedDateTime;
import org.springframework.lang.NonNull;
import com.memoblend.applicationcore.analysis.valueobject.Description;
import com.memoblend.applicationcore.analysis.valueobject.Location;
import com.memoblend.applicationcore.analysis.valueobject.ScheduleTime;
import com.memoblend.applicationcore.analysis.valueobject.Title;

/**
 * スケジュールのエンティティです。
 */
public class Schedule {
  @NonNull
  private ScheduleTime startTime;
  @NonNull
  private ScheduleTime endTime;
  @NonNull
  private Title title;
  @NonNull
  private Description description;
  @NonNull
  private Location location;

  /**
   * {@link Schedule} クラスの新しいインスタンスを初期化します。
   *
   * @param startTime   開始時間。
   * @param endTime     終了時間。
   * @param title       タイトル。
   * @param description 説明。
   * @param location    場所。
   */
  public Schedule(ZonedDateTime startTime, ZonedDateTime endTime, String title, String description, String location)
      throws ScheduleValidationException {
    this.startTime = new ScheduleTime(startTime);
    this.endTime = new ScheduleTime(endTime);
    this.title = new Title(title);
    this.description = new Description(description);
    this.location = new Location(location);
  }

  /**
   * 開始時間を取得します。
   *
   * @return 開始時間。
   */
  public ZonedDateTime getStartTime() {
    return this.startTime.getValue();
  }

  /**
   * 終了時間を取得します。
   *
   * @return 終了時間。
   */
  public ZonedDateTime getEndTime() {
    return this.endTime.getValue();
  }

  /**
   * タイトルを取得します。
   *
   * @return タイトル。
   */
  public String getTitle() {
    return this.title.getValue();
  }

  /**
   * 説明を取得します。
   *
   * @return 説明。
   */
  public String getDescription() {
    return this.description.getValue();
  }

  /**
   * 場所を取得します。
   *
   * @return 場所。
   */
  public String getLocation() {
    return this.location.getValue();
  }

  /**
   * 開始時間を設定します。
   *
   * @param startTime 開始時間。
   */
  public void setStartTime(ZonedDateTime startTime) {
    this.startTime = new ScheduleTime(startTime);
  }

  /**
   * 終了時間を設定します。
   *
   * @param endTime 終了時間。
   */
  public void setEndTime(ZonedDateTime endTime) {
    this.endTime = new ScheduleTime(endTime);
  }

  /**
   * タイトルを設定します。
   *
   * @param title タイトル。
   */
  public void setTitle(String title) throws ScheduleValidationException {
    this.title = new Title(title);
  }

  /**
   * 説明を設定します。
   *
   * @param description 説明。
   */
  public void setDescription(String description) throws ScheduleValidationException {
    this.description = new Description(description);
  }

  /**
   * 場所を設定します。
   *
   * @param location 場所。
   */
  public void setLocation(String location) throws ScheduleValidationException {
    this.location = new Location(location);
  }
}
