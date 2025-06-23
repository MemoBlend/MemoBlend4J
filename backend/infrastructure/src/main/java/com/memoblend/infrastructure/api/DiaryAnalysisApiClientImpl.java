package com.memoblend.infrastructure.api;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.memoblend.applicationcore.api.DiaryAnalysisApiClient;
import com.memoblend.applicationcore.api.ExternalApiException;
import com.memoblend.applicationcore.constant.ApiNameConstants;
import lombok.AllArgsConstructor;

/**
 * 日記の分析 API を呼び出すためのクライアント実装です。
 */
@Component
@AllArgsConstructor
public class DiaryAnalysisApiClientImpl implements DiaryAnalysisApiClient {
  private final RestTemplate restTemplate;

  @Override
  public boolean postDiaryAnalysis(Long userId, Long diaryId, String content) throws ExternalApiException {
    final String url = String.format("http://localhost:8000/api/diary/%d", userId);
    final DiaryAnalysisApiPostRequest request = new DiaryAnalysisApiPostRequest(diaryId, content);
    try {
      restTemplate.postForEntity(url, request, String.class);
      return true;
    } catch (Exception e) {
      throw new ExternalApiException(ApiNameConstants.AnalysisAPI);
    }
  }

  @Override
  public JsonNode getRecommendedSchedule(long userId) throws ExternalApiException {
    final String url = String.format("http://localhost:8000/api/schedule/%d", userId);
    try {
      return restTemplate.getForObject(url, JsonNode.class);
    } catch (Exception e) {
      throw new ExternalApiException(ApiNameConstants.AnalysisAPI);
    }
  }
}
