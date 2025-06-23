package com.memoblend.web.controller;

import org.springframework.web.bind.annotation.RestController;
import com.memoblend.applicationcore.api.ExternalApiException;
import com.memoblend.applicationcore.applicationservice.DiaryApplicationService;
import com.memoblend.applicationcore.auth.PermissionDeniedException;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryNotFoundException;
import com.memoblend.applicationcore.diary.DiaryValidationException;
import com.memoblend.systemcommon.constant.CommonExceptionIdConstants;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import com.memoblend.web.controller.dto.diary.GetDiariesResponse;
import com.memoblend.web.controller.dto.diary.GetDiaryResponse;
import com.memoblend.web.controller.dto.diary.PostDiaryRequest;
import com.memoblend.web.controller.dto.diary.PutDiaryRequest;
import com.memoblend.web.controller.mapper.diary.GetDiariesResponseMapper;
import com.memoblend.web.controller.mapper.diary.GetDiaryResponseMapper;
import com.memoblend.web.controller.mapper.diary.PostDiaryRequestMapper;
import com.memoblend.web.controller.mapper.diary.PutDiaryRequestMapper;
import com.memoblend.web.controller.util.ProblemDetailsFactory;
import com.memoblend.web.log.ErrorMessageBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import java.net.URI;
import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 日記情報の操作を行うコントローラークラスです。
 */
@RestController
@RequestMapping("api/diary")
@Tag(name = "Diary", description = "日記の情報にアクセスする API です。")
@AllArgsConstructor
public class DiaryController {
  private final DiaryApplicationService diaryApplicationService;
  private final ProblemDetailsFactory problemDetailsFactory;
  private static final Logger apLog = LoggerFactory.getLogger(SystemPropertyConstants.APPLICATION_LOGGER);

  /**
   * 年月を指定して、日記を取得します。
   * 
   * @return 日記情報。
   * @throws PermissionDeniedException 権限エラーが起きた場合。
   */
  @Operation(summary = "年月を指定して、日記を取得します。", description = "年月を指定して、日記を取得します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "成功。", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetDiariesResponse.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "対応した日記が存在しません。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
  })
  @GetMapping()
  public ResponseEntity<GetDiariesResponse> getDiaries(@RequestParam(required = false) Integer year,
      @RequestParam(required = false) Integer month)
      throws PermissionDeniedException {
    List<Diary> diaries = diaryApplicationService.getDiariesByYearAndMonth(year, month);
    GetDiariesResponse response = GetDiariesResponseMapper.convert(diaries);
    return ResponseEntity.ok().body(response);
  }

  /**
   * UserID を指定して、日記を全件取得します。
   * 
   * @return 日記情報。
   * @throws PermissionDeniedException 権限エラーが起きた場合。
   */
  @Operation(summary = "UserID を指定して、日記を全件取得します。", description = "UserID を指定して、日記を全件取得します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "成功。", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetDiariesResponse.class))),
      @ApiResponse(responseCode = "400", description = "リクエストエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "対応した日記が存在しません。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
  })
  @GetMapping("user/{userId}")
  public ResponseEntity<GetDiariesResponse> getDiariesByUserId(@PathVariable Long userId)
      throws PermissionDeniedException {
    List<Diary> diaries = diaryApplicationService.getDiariesByUserId(userId);
    GetDiariesResponse response = GetDiariesResponseMapper.convert(diaries);
    return ResponseEntity.ok().body(response);
  }

  /**
   * ID を指定して、日記情報を取得します。
   * 
   * @param id 日記の ID 。
   * @return 日記情報。
   * @throws PermissionDeniedException 権限エラーが起きた場合。
   */
  @Operation(summary = "ID を指定して、日記情報を取得します。", description = "ID を指定して、日記情報を取得します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "成功。", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetDiaryResponse.class))),
      @ApiResponse(responseCode = "400", description = "リクエストエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "対応した日記が存在しません。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
  })
  @GetMapping("{id}")
  public ResponseEntity<?> getDiary(@PathVariable("id") long id) throws PermissionDeniedException {
    Diary diary = null;
    try {
      diary = diaryApplicationService.getDiary(id);
    } catch (DiaryNotFoundException e) {
      apLog.info(e.getMessage());
      apLog.debug(ExceptionUtils.getStackTrace(e));
      ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, e.getExceptionId(),
          e.getLogMessageValue(), e.getFrontMessageValue());
      ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
          CommonExceptionIdConstants.E_BUSINESS, HttpStatus.NOT_FOUND);
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .contentType(MediaType.APPLICATION_PROBLEM_JSON)
          .body(problemDetail);
    }
    GetDiaryResponse response = GetDiaryResponseMapper.convert(diary);
    return ResponseEntity.ok().body(response);
  }

  /**
   * 日記情報を登録します。
   * 
   * @param request 日記情報。
   * @return 登録結果。
   * @throws PermissionDeniedException 権限エラーが起きた場合。
   * @throws DiaryValidationException  日記が不正な場合。
   * @throws ExternalApiException      外部APIエラーが発生した場合。
   */
  @Operation(summary = "日記情報を登録します。", description = "日記情報を登録します。")
  @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "成功。", content = @Content),
      @ApiResponse(responseCode = "400", description = "リクエストエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "日記の登録に失敗しました。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
  })
  @PostMapping
  public ResponseEntity<?> postDiary(@RequestBody PostDiaryRequest request)
      throws PermissionDeniedException, DiaryValidationException, ExternalApiException {
    Diary diary = PostDiaryRequestMapper.convert(request);
    Diary addedDiary = diaryApplicationService.addDiary(diary);
    return ResponseEntity.created(URI.create("/api/diary/" + addedDiary.getId())).build();
  }

  /**
   * ID を指定して、日記情報を削除します。
   * 
   * @param id 日記の ID 。
   * @return 削除結果。
   * @throws PermissionDeniedException 権限エラーが起きた場合。
   */
  @Operation(summary = "ID を指定して、日記情報を削除します。", description = "ID を指定して、日記情報を削除します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "成功。", content = @Content),
      @ApiResponse(responseCode = "400", description = "リクエストエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "対応した日記が存在しません。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
  })
  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteDiary(@PathVariable("id") long id) throws PermissionDeniedException {
    try {
      diaryApplicationService.deleteDiary(id);
    } catch (DiaryNotFoundException e) {
      apLog.info(e.getMessage());
      apLog.debug(ExceptionUtils.getStackTrace(e));
      ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, e.getExceptionId(),
          e.getLogMessageValue(), e.getFrontMessageValue());
      ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
          CommonExceptionIdConstants.E_BUSINESS, HttpStatus.NOT_FOUND);
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .contentType(MediaType.APPLICATION_PROBLEM_JSON)
          .body(problemDetail);
    }
    return ResponseEntity.ok().build();
  }

  /**
   * 日記情報を更新します。
   * 
   * @param request 日記情報。
   * @return 更新結果。
   * @throws PermissionDeniedException 権限エラーが起きた場合。
   * @throws DiaryValidationException  日記が不正な場合。
   */
  @Operation(summary = "日記情報を更新します。", description = "日記情報を更新します。")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "成功。", content = @Content),
      @ApiResponse(responseCode = "400", description = "リクエストエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "401", description = "未認証。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "404", description = "対応した日記が存在しません。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
      @ApiResponse(responseCode = "500", description = "サーバーエラー。", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class))),
  })
  @PutMapping
  public ResponseEntity<?> putDiary(@RequestBody PutDiaryRequest request)
      throws PermissionDeniedException, DiaryValidationException {
    Diary diary = PutDiaryRequestMapper.convert(request);
    try {
      diaryApplicationService.updateDiary(diary);
    } catch (DiaryNotFoundException e) {
      apLog.info(e.getMessage());
      apLog.debug(ExceptionUtils.getStackTrace(e));
      ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder(e, e.getExceptionId(),
          e.getLogMessageValue(), e.getFrontMessageValue());
      ProblemDetail problemDetail = problemDetailsFactory.createProblemDetail(errorBuilder,
          CommonExceptionIdConstants.E_BUSINESS, HttpStatus.NOT_FOUND);
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .contentType(MediaType.APPLICATION_PROBLEM_JSON)
          .body(problemDetail);
    }
    return ResponseEntity.ok().build();
  }
}
