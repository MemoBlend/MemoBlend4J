package com.memoblend.web.controller.mapper.analysis;

import java.util.List;
import com.memoblend.applicationcore.analysis.Schedule;
import com.memoblend.web.controller.dto.analysis.GetRecommendedSchedulesResponse;

/**
 * userId と {@link Schedule} のリストを {@link GetRecommendedSchedulesResponse}
 * に変換するクラスです。
 */
public class GetRecommendedSchedulesResponseMapper {
  /**
   * userIdと{@link Schedule} のリストを {@link GetRecommendedSchedulesResponse} に変換します。
   * 
   * @param userId    ユーザーID。
   * @param schedules {@link Schedule} のリスト。
   * @return {@link GetRecommendedSchedulesResponse} のインスタンス。
   */
  public static GetRecommendedSchedulesResponse convert(long userId, List<Schedule> schedules) {
    GetRecommendedSchedulesResponse response = new GetRecommendedSchedulesResponse(userId, schedules);
    return response;
  }
}
