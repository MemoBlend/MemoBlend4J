package com.memoblend.infrastructure.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分析 API への日記の送信リクエストクラスです。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryAnalysisApiPostRequest {
  private Long id;
  @JsonProperty("diary_text")
  private String diaryText;
}
