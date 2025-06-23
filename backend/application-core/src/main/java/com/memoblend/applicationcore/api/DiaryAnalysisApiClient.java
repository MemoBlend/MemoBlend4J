package com.memoblend.applicationcore.api;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 日記の分析 API を呼び出すためのクライアントインターフェースです。
 */
public interface DiaryAnalysisApiClient {
  /**
   * 日記の内容を分析するための API を呼び出します。
   * 成功したら true を返し、失敗した場合は {@link ExternalApiException} をスローします。
   * 
   * @param userId  ユーザー ID 。
   * @param diaryId 日記 ID 。
   * @param content 日記の内容。
   * @throws ExternalApiException 外部 API の呼び出しに失敗した場合にスローされます。
   */
  boolean postDiaryAnalysis(Long userId, Long diaryId, String content) throws ExternalApiException;

  /**
   * ユーザーのおすすめスケジュールを取得するための API を呼び出します。
   *
   * @param userId ユーザー ID 。
   * @return おすすめスケジュールのレスポンス（JSONをJsonNodeで返却）。
   * @throws ExternalApiException 外部 API の呼び出しに失敗した場合にスローされます。
   */
  JsonNode getRecommendedSchedule(long userId) throws ExternalApiException;
}
