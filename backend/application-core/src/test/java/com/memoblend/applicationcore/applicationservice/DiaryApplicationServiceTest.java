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
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryAlreadyExistException;
import com.memoblend.applicationcore.diary.DiaryDomainService;
import com.memoblend.applicationcore.diary.DiaryNotFoundException;
import com.memoblend.applicationcore.diary.DiaryRepository;

/**
 * 日記のアプリケーションサービスのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.messages.basename=applicationcore.messages")
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
public class DiaryApplicationServiceTest {

  @Mock
  private DiaryRepository diaryRepository;

  @Mock
  private DiaryDomainService diaryDomainService;

  @Autowired
  private MessageSource messages;

  private DiaryApplicationService diaryApplicationService;

  @BeforeEach
  void setUp() {
    diaryApplicationService = new DiaryApplicationService(diaryRepository, diaryDomainService, messages);
  }

  @Test
  void testGetDiaries_正常系_リポジトリのfindAllを1回呼び出す() {
    // Arrange
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findAll()).thenReturn(diaries);
    // Act
    diaryApplicationService.getDiaries();
    // Assert
    verify(diaryRepository, times(1)).findAll();
  }

  @Test
  void testGetDiaries_正常系_日記のリストを返す() {
    // Arrange
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(2025, 1, 1));
    List<Diary> diaries = createDiaries(dates);
    when(diaryRepository.findAll()).thenReturn(diaries);
    // Act
    List<Diary> actual = diaryApplicationService.getDiaries();
    // Assert
    assertThat(actual).isEqualTo(diaries);
  }

  @Test
  void testGetDiary_正常系_リポジトリのfindByDateを1回呼び出す() throws DiaryNotFoundException {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryRepository.findByDate(date)).thenReturn(diary);
    // Act
    diaryApplicationService.getDiary(date);
    // Assert
    verify(diaryRepository, times(1)).findByDate(date);
  }

  @Test
  void testGetDiary_正常系_指定した日付の日記を返す() throws DiaryNotFoundException {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryRepository.findByDate(date)).thenReturn(diary);
    // Act
    Diary actual = diaryApplicationService.getDiary(date);
    // Assert
    assertThat(actual).isEqualTo(diary);
  }

  @Test
  void testGetDiary_異常系_指定した日付の日記が存在しない場合DiaryNotFoundExceptionがスローされる() {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    when(diaryRepository.findByDate(date)).thenReturn(null);
    // Act
    Executable action = () -> {
      diaryApplicationService.getDiary(date);
    };
    // Assert
    assertThrows(DiaryNotFoundException.class, action);
  }

  @Test
  void testAddDiary_正常系_リポジトリのaddを1回呼び出す() throws DiaryAlreadyExistException {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryDomainService.isExistDiary(diary)).thenReturn(false);
    when(diaryRepository.add(diary)).thenReturn(diary);
    // Act
    diaryApplicationService.addDiary(diary);
    // Assert
    verify(diaryRepository, times(1)).add(diary);
  }

  @Test
  void testAddDiary_正常系_追加された日記を返す() throws DiaryAlreadyExistException {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryDomainService.isExistDiary(diary)).thenReturn(false);
    when(diaryRepository.add(diary)).thenReturn(diary);
    // Act
    Diary actual = diaryApplicationService.addDiary(diary);
    // Assert
    assertThat(actual).isEqualTo(diary);
  }

  @Test
  void testAddDiary_異常系_追加しようとした日記が既に存在する場合DiaryAlreadyExistExceptionがスローされる() {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryDomainService.isExistDiary(diary)).thenReturn(true);
    // Act
    Executable action = () -> {
      diaryApplicationService.addDiary(diary);
    };
    // Assert
    assertThrows(DiaryAlreadyExistException.class, action);
  }

  @Test
  void testUpdateDiary_正常系_リポジトリのupdateを1回呼び出す() throws DiaryNotFoundException {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryDomainService.isExistDiary(diary)).thenReturn(true);
    // Act
    diaryApplicationService.updateDiary(diary);
    // Assert
    verify(diaryRepository, times(1)).update(diary);
  }

  @Test
  void testUpdateDiary_異常系_更新しようとした日記が存在しない場合DiaryNotFoundExceptionがスローされる() {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryDomainService.isExistDiary(diary)).thenReturn(false);
    // Act
    Executable action = () -> {
      diaryApplicationService.updateDiary(diary);
    };
    // Assert
    assertThrows(DiaryNotFoundException.class, action);
  }

  @Test
  void testDeleteDiary_正常系_リポジトリのdeleteを1回呼び出す() throws DiaryNotFoundException {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    Diary diary = createDiary(date);
    when(diaryRepository.findByDate(date)).thenReturn(diary);
    // Act
    diaryApplicationService.deleteDiary(date);
    // Assert
    verify(diaryRepository, times(1)).delete(diary.getId());
  }

  @Test
  void testDeleteDiary_異常系_削除しようとした日記が存在しない場合DiaryNotFoundExceptionがスローされる() {
    // Arrange
    LocalDate date = LocalDate.of(2025, 1, 1);
    when(diaryRepository.findByDate(date)).thenReturn(null);
    // Act
    Executable action = () -> {
      diaryApplicationService.deleteDiary(date);
    };
    // Assert
    assertThrows(DiaryNotFoundException.class, action);
  }

  private List<Diary> createDiaries(List<LocalDate> dates) {
    List<Diary> diaries = new ArrayList<>();
    for (LocalDate date : dates) {
      diaries.add(createDiary(date));
    }
    return diaries;
  }

  private Diary createDiary(LocalDate date) {
    long id = 1;
    long userId = 1;
    String title = "testTitle";
    String content = "testContent";
    Diary diary = new Diary(id, userId, title, content, date);
    return diary;
  }
}
