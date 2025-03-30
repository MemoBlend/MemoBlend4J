package com.memoblend.applicationcore.applicationservice;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.memoblend.applicationcore.analysis.Schedule;
import com.memoblend.applicationcore.analysis.ScheduleValidationException;
import com.memoblend.applicationcore.auth.PermissionDeniedException;
import com.memoblend.applicationcore.auth.UserStore;
import com.memoblend.applicationcore.constant.MessageIdConstants;
import com.memoblend.applicationcore.constant.UserRoleConstants;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryRepository;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import lombok.AllArgsConstructor;

/**
 * AI分析のアプリケーションサービス。
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AnalysisApplicationService {
  @Autowired
  private DiaryRepository diaryRepository;
  private MessageSource messages;
  @Autowired
  private UserStore userStore;
  private final Logger apLog = Logger.getLogger(SystemPropertyConstants.APPLICATION_LOGGER);

  /**
   * ユーザー IDに基づいて、 AI 分析の結果として取得したスケジュールのリストを返します。
   * 
   * @param userId ユーザー ID 。
   * @return {@link Schedule} のリスト。
   * @throws PermissionDeniedException   権限が足りなかった場合。
   * @throws ScheduleValidationException スケジュールのバリデーションに失敗した場合。
   */
  public List<Schedule> getRecommendedSchedulesByUserId(long userId)
      throws PermissionDeniedException, ScheduleValidationException {
    apLog.info(
        messages.getMessage(MessageIdConstants.D_ANALYSIS_GET_RECOMMENDED_SCHEDULES_BY_USER_ID,
            new Object[] { userId },
            Locale.getDefault()));
    List<Diary> diaries = diaryRepository.findByUserId(userId);
    if (!userStore.isInRole(UserRoleConstants.USER)) {
      throw new PermissionDeniedException("getRecommendedSchedulesByUserId");
    }
    return createDummySchedules();
  }

  private List<Schedule> createDummySchedules() throws ScheduleValidationException {
    return List.of(
        new Schedule(
            ZonedDateTime.parse("2025-04-01T08:30:00Z"),
            ZonedDateTime.parse("2025-04-01T09:00:00Z"),
            "メール確認", "昨日以降の未読メールをチェックし、今日のタスクリストを更新。特に緊急度の高い案件の有無を確認し、必要に応じてスケジュール調整を実施", "デスク"),
        new Schedule(
            ZonedDateTime.parse("2025-04-01T09:00:00Z"),
            ZonedDateTime.parse("2025-04-01T10:30:00Z"),
            "朝会", "先週の成果物のレビュー結果を共有し、今週のスプリントで取り組むタスクの優先順位付けと工数見積もりを実施。チーム内の懸念事項も共有", "会議室A"),
        new Schedule(
            ZonedDateTime.parse("2025-04-01T11:00:00Z"),
            ZonedDateTime.parse("2025-04-01T12:00:00Z"),
            "クライアントMTG", "次期システムの要件定義書の最終確認とスケジュールの調整。開発チームから提案された技術的な代替案についても議論し、予算との整合性を確認", "オンライン"),
        new Schedule(
            ZonedDateTime.parse("2025-04-01T12:15:00Z"),
            ZonedDateTime.parse("2025-04-01T12:45:00Z"),
            "1on1ミーティング", "前四半期の目標達成状況を振り返り、新四半期の目標設定を実施。キャリアパスについての相談と、現在抱えている技術的な課題の解決策を議論", "面談室C"),
        new Schedule(
            ZonedDateTime.parse("2025-04-01T13:00:00Z"),
            ZonedDateTime.parse("2025-04-01T14:00:00Z"),
            "チームランチ", "先週入社した新メンバーの歓迎会を兼ねたランチミーティング。各メンバーの得意分野や興味のある技術領域について情報交換を実施", "社員食堂"),
        new Schedule(
            ZonedDateTime.parse("2025-04-01T14:30:00Z"),
            ZonedDateTime.parse("2025-04-01T16:00:00Z"),
            "開発作業", "OAuth2.0を使用した認証機能の実装と単体テストの作成。SecurityConfigの設定を見直し、カスタム認証フィルターの追加も検討", "デスク"),
        new Schedule(
            ZonedDateTime.parse("2025-04-01T16:30:00Z"),
            ZonedDateTime.parse("2025-04-01T17:30:00Z"),
            "デイリーレビュー", "本日実装完了した認証機能のコードレビューを実施。明日以降の作業計画を確認し、想定されるリスクと対策について技術的な観点から議論", "会議室B"),
        new Schedule(
            ZonedDateTime.parse("2025-04-01T17:45:00Z"),
            ZonedDateTime.parse("2025-04-01T18:15:00Z"),
            "勉強会", "Spring Boot 3.0の主要な更新内容と移行のポイントについて解説。特にネイティブイメージのビルドとObservabilityの機能を中心に議論",
            "オンライン"));
  }
}