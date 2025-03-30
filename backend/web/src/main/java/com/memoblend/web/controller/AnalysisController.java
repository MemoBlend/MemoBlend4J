package com.memoblend.web.controller;

import org.springframework.web.bind.annotation.RestController;
import com.memoblend.applicationcore.analysis.Schedule;
import com.memoblend.applicationcore.analysis.ScheduleValidationException;
import com.memoblend.applicationcore.applicationservice.AnalysisApplicationService;
import com.memoblend.applicationcore.auth.PermissionDeniedException;
import com.memoblend.web.controller.dto.analysis.GetRecommendedSchedulesResponse;
import com.memoblend.web.controller.dto.diary.GetDiariesResponse;
import com.memoblend.web.controller.mapper.analysis.GetRecommendedSchedulesResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AI分析を行うコントローラークラスです。
 */
@RestController
@RequestMapping("api/analysis")
@Tag(name = "Analysis", description = "AI分析の情報にアクセスする API です。")
@AllArgsConstructor
public class AnalysisController {
  @Autowired
  AnalysisApplicationService analysisApplicationService;

  /**
   * ユーザーIDを指定して、過去の日記からおすすめの予定を取得します。
   * 
   * @param userId ユーザー ID 。
   * @return おすすめの予定。
   * @throws PermissionDeniedException   ユーザーの権限がない場合。
   * @throws ScheduleValidationException スケジュールのバリデーションエラー。
   */
  @Operation(summary = "ユーザーIDを指定して、過去の日記からおすすめの予定を取得します。", description = "ユーザーIDを指定して、過去の日記からおすすめの予定を取得します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "成功。", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetRecommendedSchedulesResponse.class))),
      @ApiResponse(responseCode = "400", description = "リクエストエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "対応した日記が存在しません。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
  })
  @GetMapping("{userId}")
  public ResponseEntity<GetRecommendedSchedulesResponse> getRecommendedSchedulesByUserId(
      @PathVariable("userId") long userId) throws PermissionDeniedException, ScheduleValidationException {
    List<Schedule> schedules = analysisApplicationService.getRecommendedSchedulesByUserId(userId);
    GetRecommendedSchedulesResponse response = GetRecommendedSchedulesResponseMapper.convert(userId, schedules);
    return ResponseEntity.ok().body(response);
  }
}