package com.memoblend.applicationcore.applicationservice;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.function.Executable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.memoblend.applicationcore.auth.PermissionDeniedException;
import com.memoblend.applicationcore.auth.UserStore;
import com.memoblend.applicationcore.constant.UserRoleConstants;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryDomainService;
import com.memoblend.applicationcore.diary.DiaryNotFoundException;
import com.memoblend.applicationcore.diary.DiaryRepository;
import com.memoblend.applicationcore.diary.DiaryValidationException;

/**
 * 日記のアプリケーションサービスのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.messages.basename=applicationcore.messages")
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
class DiaryApplicationServiceTest {

  @Mock
  private DiaryRepository diaryRepository;

  @Mock
  private DiaryDomainService diaryDomainService;

  @Autowired
  private MessageSource messages;

  @Mock
  private UserStore userStore;

  private DiaryApplicationService diaryApplicationService;

  @BeforeEach
  void setUp() {
    diaryApplicationService = new DiaryApplicationService(diaryRepository, diaryDomainService, messages, userStore);
  }

  @Test
  void testGetDiaries_正常系_リポジトリのfindAllを1回呼び出す() throws PermissionDeniedException, DiaryValidationException {
    // Arrange
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findAll()).thenReturn(diaries);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    diaryApplicationService.getDiaries();
    // Assert
    verify(diaryRepository, times(1)).findAll();
  }

  @Test
  void testGetDiaries_正常系_日記のリストを返す() throws PermissionDeniedException, DiaryValidationException {
    // Arrange
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findAll()).thenReturn(diaries);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    List<Diary> actual = diaryApplicationService.getDiaries();
    // Assert
    assertThat(actual).isEqualTo(diaries);
  }

  @Test
  void testGetDiaries_異常系_権限がない場合にPermissionDeniedExceptionが発生する() throws DiaryValidationException {
    // Arrange
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findAll()).thenReturn(diaries);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(false);
    // Act
    Executable action = () -> {
      diaryApplicationService.getDiaries();
    };
    // Assert
    assertThrows(PermissionDeniedException.class, action);
  }

  @Test
  void testGetDiariesByUserId_正常系_リポジトリのfindByUserIdを1回呼び出す()
      throws PermissionDeniedException, DiaryValidationException {
    // Arrange
    long userId = 1;
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findByUserId(userId)).thenReturn(diaries);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    diaryApplicationService.getDiariesByUserId(userId);
    // Assert
    verify(diaryRepository, times(1)).findByUserId(userId);
  }

  @Test
  void testGetDiariesByUserId_正常系_日記のリストを返す() throws PermissionDeniedException, DiaryValidationException {
    // Arrange
    long userId = 1;
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findByUserId(userId)).thenReturn(diaries);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    List<Diary> actual = diaryApplicationService.getDiariesByUserId(userId);
    // Assert
    assertThat(actual).isEqualTo(diaries);
  }

  @Test
  void testGetDiariesByUserId_異常系_権限がない場合にPermissionDeniedExceptionが発生する() throws DiaryValidationException {
    // Arrange
    long userId = 1;
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findByUserId(userId)).thenReturn(diaries);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(false);
    // Act
    Executable action = () -> {
      diaryApplicationService.getDiariesByUserId(userId);
    };
    // Assert
    assertThrows(PermissionDeniedException.class, action);
  }

  @Test
  void testGetDiary_正常系_リポジトリのfindByIdを1回呼び出す()
      throws DiaryNotFoundException, PermissionDeniedException, DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryRepository.findById(id)).thenReturn(diary);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    diaryApplicationService.getDiary(id);
    // Assert
    verify(diaryRepository, times(1)).findById(id);
  }

  @Test
  void testGetDiary_正常系_指定した作成日時の日記を返す()
      throws DiaryNotFoundException, PermissionDeniedException, DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryRepository.findById(id)).thenReturn(diary);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    Diary actual = diaryApplicationService.getDiary(id);
    // Assert
    assertThat(actual).isEqualTo(diary);
  }

  @Test
  void testGetDiary_異常系_指定したidの日記が存在しない場合DiaryNotFoundExceptionがスローされる() {
    // Arrange
    long id = 1;
    when(diaryRepository.findById(id)).thenReturn(null);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    Executable action = () -> {
      diaryApplicationService.getDiary(id);
    };
    // Assert
    assertThrows(DiaryNotFoundException.class, action);
  }

  @Test
  void testGetDiary_異常系_権限がない場合にPermnissionDeniedExceptionが発生する() throws DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryRepository.findById(id)).thenReturn(diary);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(false);
    // Act
    Executable action = () -> {
      diaryApplicationService.getDiary(id);
    };
    // Assert
    assertThrows(PermissionDeniedException.class, action);
  }

  @Test
  void testAddDiary_正常系_リポジトリのaddを1回呼び出す() throws PermissionDeniedException, DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryDomainService.isExistDiary(id)).thenReturn(false);
    when(diaryRepository.add(diary)).thenReturn(diary);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    diaryApplicationService.addDiary(diary);
    // Assert
    verify(diaryRepository, times(1)).add(diary);
  }

  @Test
  void testAddDiary_正常系_追加された日記を返す() throws PermissionDeniedException, DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryDomainService.isExistDiary(id)).thenReturn(false);
    when(diaryRepository.add(diary)).thenReturn(diary);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    Diary actual = diaryApplicationService.addDiary(diary);
    // Assert
    assertThat(actual).isEqualTo(diary);
  }

  @Test
  void testAddDiary_異常系_権限がない場合にPermissionDeniedExceptionが発生する() throws DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryDomainService.isExistDiary(id)).thenReturn(false);
    when(diaryRepository.add(diary)).thenReturn(diary);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(false);
    // Act
    Executable action = () -> {
      diaryApplicationService.addDiary(diary);
    };
    // Assert
    assertThrows(PermissionDeniedException.class, action);
  }

  @Test
  void testUpdateDiary_正常系_リポジトリのupdateを1回呼び出す()
      throws DiaryNotFoundException, PermissionDeniedException, DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryDomainService.isExistDiary(id)).thenReturn(true);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    diaryApplicationService.updateDiary(diary);
    // Assert
    verify(diaryRepository, times(1)).update(diary);
  }

  @Test
  void testUpdateDiary_異常系_更新しようとした日記が存在しない場合DiaryNotFoundExceptionがスローされる() throws DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryDomainService.isExistDiary(id)).thenReturn(false);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    Executable action = () -> {
      diaryApplicationService.updateDiary(diary);
    };
    // Assert
    assertThrows(DiaryNotFoundException.class, action);
  }

  @Test
  void testUpdateDiary_異常系_権限がない場合にPermissionDeniedExceptionが発生する() throws DiaryValidationException {
    // Arrange
    LocalDate createdDate = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(createdDate);
    long id = diary.getId();
    when(diaryDomainService.isExistDiary(id)).thenReturn(true);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(false);
    // Act
    Executable action = () -> {
      diaryApplicationService.updateDiary(diary);
    };
    // Assert
    assertThrows(PermissionDeniedException.class, action);
  }

  @Test
  void testDeleteDiary_正常系_リポジトリのdeleteを1回呼び出す() throws DiaryNotFoundException, PermissionDeniedException {
    // Arrange
    long id = 1;
    when(diaryDomainService.isExistDiary(id)).thenReturn(true);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    diaryApplicationService.deleteDiary(id);
    // Assert
    verify(diaryRepository, times(1)).delete(id);
  }

  @Test
  void testDeleteDiary_異常系_削除しようとした日記が存在しない場合DiaryNotFoundExceptionがスローされる() {
    // Arrange
    long id = 1;
    when(diaryDomainService.isExistDiary(id)).thenReturn(false);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(true);
    // Act
    Executable action = () -> {
      diaryApplicationService.deleteDiary(id);
    };
    // Assert
    assertThrows(DiaryNotFoundException.class, action);
  }

  @Test
  void testDeleteDiary_異常系_権限がない場合PermissionDeniedExceptionが発生する() {
    // Arrange
    long id = 1;
    when(diaryDomainService.isExistDiary(id)).thenReturn(true);
    when(userStore.isInRole(UserRoleConstants.USER)).thenReturn(false);
    // Act
    Executable action = () -> {
      diaryApplicationService.deleteDiary(id);
    };
    // Assert
    assertThrows(PermissionDeniedException.class, action);
  }

  private List<Diary> createDiaries(List<LocalDate> dates) throws DiaryValidationException {
    List<Diary> diaries = new ArrayList<>();
    for (LocalDate createdDate : dates) {
      diaries.add(createDiary(createdDate));
    }
    return diaries;
  }

  private Diary createDiary(LocalDate createdDate) throws DiaryValidationException {
    long id = 1;
    long userId = 1;
    String title = "testTitle";
    String content = "testContent";
    Diary diary = new Diary(id, userId, title, content, createdDate, false);
    return diary;
  }
}
