package com.memoblend.web.controller.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ユーザーのおすすめスケジュールを取得するためのレスポンスクラスです。
 */
@Data
@AllArgsConstructor
public class GetRecommendedScheduleResponse {
  private String recommendedSchedule;
}