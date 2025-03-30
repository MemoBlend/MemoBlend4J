package com.memoblend.web.controller.dto.analysis;

import java.util.List;
import com.memoblend.applicationcore.analysis.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AI分析の結果として取得したスケジュールのリストを返すレスポンスDTOです。
 */
@Data
@AllArgsConstructor
public class GetRecommendedSchedulesResponse {
  private long userId;
  private List<Schedule> schedules;
}
