package com.memoblend.applicationcore.applicationservice;

import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.memoblend.applicationcore.auth.PermissionDeniedException;
import com.memoblend.applicationcore.auth.UserStore;
import com.memoblend.applicationcore.constant.MessageIdConstants;
import com.memoblend.applicationcore.constant.UserRoleConstants;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryDomainService;
import com.memoblend.applicationcore.diary.DiaryNotFoundException;
import com.memoblend.applicationcore.diary.DiaryRepository;
import com.memoblend.systemcommon.constant.SystemPropertyConstants;
import lombok.AllArgsConstructor;

/**
 * 日記のアプリケーションサービスです。
 */
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class DiaryApplicationService {

  private final DiaryRepository diaryRepository;
  private final DiaryDomainService diaryDomainService;
  private final MessageSource messages;
  private final UserStore userStore;
  private final Logger apLog = Logger.getLogger(SystemPropertyConstants.APPLICATION_LOGGER);

  /**
   * 年月を指定して、日記をリストで取得します。
   * 指定された年が null の場合、現在の年を使用します。
   * 指定された月が null または 1 から 12 の範囲外の場合、現在の月を使用します。
   * 
   * @param year  年。
   * @param month 月。
   * @return 指定した年月の日記のリスト。
   * @throws PermissionDeniedException 認可が拒否された場合。
   */
  public List<Diary> getDiariesByYearAndMonth(Integer year, Integer month) throws PermissionDeniedException {
    if (year == null) {
      year = LocalDate.now().getYear();
    }
    if (month == null || month < 1 || month > 12) {
      month = LocalDate.now().getMonthValue();
    }
    apLog.info(messages.getMessage(MessageIdConstants.D_DIARY_GET_DIARIES_BY_YEAR_AND_MONTH,
        new Object[] { year, month }, Locale.getDefault()));
    if (!userStore.isInRole(UserRoleConstants.USER)) {
      throw new PermissionDeniedException("getDiariesByYearAndMonth");
    }
    return diaryRepository.findByYearAndMonth(year, month);
  }

  /**
   * ユーザー ID を指定して、日記をリストで取得します。
   * 
   * @param userId ユーザー ID 。
   * @return 日記のリスト。
   * @throws PermissionDeniedException 認可が拒否された場合。
   */
  public List<Diary> getDiariesByUserId(long userId) throws PermissionDeniedException {
    apLog.info(messages.getMessage(MessageIdConstants.D_DIARY_GET_DIARIES_BY_USER_ID,
        new Object[] { userId }, Locale.getDefault()));
    if (!userStore.isInRole(UserRoleConstants.USER)) {
      throw new PermissionDeniedException("getDiariesByUserId");
    }
    return diaryRepository.findByUserId(userId);
  }

  /**
   * ID を指定して、日記を取得します。
   * 
   * @param id 日記の ID 。
   * @return 条件に合う日記。
   * @throws DiaryNotFoundException    日記が見つからない場合。
   * @throws PermissionDeniedException 認可が拒否された場合。
   */
  public Diary getDiary(long id) throws DiaryNotFoundException, PermissionDeniedException {
    apLog.info(messages.getMessage(MessageIdConstants.D_DIARY_GET_DIARY,
        new Object[] { id }, Locale.getDefault()));
    if (!userStore.isInRole(UserRoleConstants.USER)) {
      throw new PermissionDeniedException("getDiary");
    }
    Diary diary = diaryRepository.findById(id);
    if (diary == null) {
      throw new DiaryNotFoundException(id);
    }
    return diary;
  }

  /**
   * 日記を追加します。
   * 
   * @param diary 追加する日記。
   * @return 追加された日記。
   * @throws PermissionDeniedException 認可が拒否された場合。
   */
  public Diary addDiary(Diary diary) throws PermissionDeniedException {
    final LocalDate createdDate = diary.getCreatedDate();
    apLog.info(messages.getMessage(MessageIdConstants.D_DIARY_ADD_DIARY,
        new Object[] { createdDate.getYear(), createdDate.getMonthValue(), createdDate.getDayOfMonth() },
        Locale.getDefault()));
    if (!userStore.isInRole(UserRoleConstants.USER)) {
      throw new PermissionDeniedException("addDiary");
    }
    return diaryRepository.add(diary);
  }

  /**
   * 既存の日記の内容を更新します。
   * 
   * @param diary 更新する日記。
   * @throws DiaryNotFoundException    日記が見つからない場合。
   * @throws PermissionDeniedException 認可が拒否された場合。
   */
  public void updateDiary(Diary diary) throws DiaryNotFoundException, PermissionDeniedException {
    final long id = diary.getId();
    apLog.info(messages.getMessage(MessageIdConstants.D_DIARY_UPDATE_DIARY,
        new Object[] { id }, Locale.getDefault()));
    if (!userStore.isInRole(UserRoleConstants.USER)) {
      throw new PermissionDeniedException("updateDiary");
    }
    if (!diaryDomainService.isExistDiary(id)) {
      throw new DiaryNotFoundException(id);
    }
    diaryRepository.update(diary);
  }

  /**
   * ID を指定して、日記を削除します。
   * 
   * @param id 日記の ID 。
   * @throws DiaryNotFoundException    日記が見つからない場合。
   * @throws PermissionDeniedException 認可が拒否された場合。
   */
  public void deleteDiary(long id) throws DiaryNotFoundException, PermissionDeniedException {
    apLog.info(messages.getMessage(MessageIdConstants.D_DIARY_DELETE_DIARY,
        new Object[] { id }, Locale.getDefault()));
    if (!userStore.isInRole(UserRoleConstants.USER)) {
      throw new PermissionDeniedException("deleteDiary");
    }
    if (!diaryDomainService.isExistDiary(id)) {
      throw new DiaryNotFoundException(id);
    }
    diaryRepository.delete(id);
  }
}
