package com.memoblend.web.controller.dto.diary;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * ユーザーのおすすめスケジュールを取得するためのレスポンスクラスです。
 */
@Data
public class GetRecommendedScheduleResponse {
  private final JsonNode recommendedSchedule;
}