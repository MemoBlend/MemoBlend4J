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
            ZonedDateTime.parse("2025-04-05T07:30:00Z"),
            ZonedDateTime.parse("2025-04-05T08:30:00Z"),
            "モーニングヨガ", "自宅でストレッチとヨガを行い、一日のスタートを快適に。深い呼吸と共に心身をリフレッシュし、休日の活力を養う", "自宅リビング"),
        new Schedule(
            ZonedDateTime.parse("2025-04-05T09:00:00Z"),
            ZonedDateTime.parse("2025-04-05T10:00:00Z"),
            "ブランチ", "お気に入りのカフェで週末限定のパンケーキとフレッシュジュースを楽しむ。読みかけの小説も持参して、ゆったりとした時間を過ごす", "近所のカフェ"),
        new Schedule(
            ZonedDateTime.parse("2025-04-05T10:30:00Z"),
            ZonedDateTime.parse("2025-04-05T12:30:00Z"),
            "ショッピング", "新しい季節の洋服を見に行きつつ、週末のセールも活用。必要な日用品のストックも確認しながら、計画的に買い物を楽しむ", "ショッピングモール"),
        new Schedule(
            ZonedDateTime.parse("2025-04-05T13:00:00Z"),
            ZonedDateTime.parse("2025-04-05T14:30:00Z"),
            "ランチ会", "学生時代の友人との久しぶりの再会。最近の生活や近況について話しながら、おいしい料理とゆっくりした時間を共有する", "イタリアンレストラン"),
        new Schedule(
            ZonedDateTime.parse("2025-04-05T15:00:00Z"),
            ZonedDateTime.parse("2025-04-05T16:00:00Z"),
            "映画鑑賞", "話題の新作映画を映画館で鑑賞。ポップコーンを片手にリラックスしながら、ストーリーに没頭する", "映画館"),
        new Schedule(
            ZonedDateTime.parse("2025-04-05T16:30:00Z"),
            ZonedDateTime.parse("2025-04-05T17:30:00Z"),
            "公園散歩", "近所の公園を散歩しながら、自然の中でリフレッシュ。ベンチに座って読書を楽しむ時間も設ける", "公園"),
        new Schedule(
            ZonedDateTime.parse("2025-04-05T18:00:00Z"),
            ZonedDateTime.parse("2025-04-05T19:00:00Z"),
            "ディナー", "家族と一緒に自宅で手料理を楽しむ。新しいレシピに挑戦しながら、楽しい会話と共に食事を満喫する", "自宅ダイニング"),
        new Schedule(
            ZonedDateTime.parse("2025-04-05T19:30:00Z"),
            ZonedDateTime.parse("2025-04-05T20:30:00Z"),
            "読書", "お気に入りの小説を読み進める。静かな時間を過ごしながら、物語の世界に浸る", "自宅書斎"));
  }
}