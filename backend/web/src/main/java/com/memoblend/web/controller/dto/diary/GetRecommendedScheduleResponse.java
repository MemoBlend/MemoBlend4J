package com.memoblend.web.controller.dto.diary;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ユーザーのおすすめスケジュールを取得するためのレスポンスクラスです。
 */
@Data
@AllArgsConstructor
public class GetRecommendedScheduleResponse {
  private JsonNode recommendedSchedule;
}