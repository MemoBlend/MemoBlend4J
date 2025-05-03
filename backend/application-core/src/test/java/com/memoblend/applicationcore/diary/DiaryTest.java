package com.memoblend.applicationcore.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;

/**
 * Diary クラスのテストクラスです。
 */
class DiaryTest {

  @Test
  void testNoError_正常系_日記を作成できる() {
    assertDoesNotThrow(() -> new Diary(1L, 1L, "testTitle", "testContent", LocalDate.of(2025, 1, 1), false));
  }

  @Test
  void testTitleIsNull_異常系_titleがnull() {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, null, "testContent", LocalDate.of(2025, 1, 1), false));
    assertEquals(ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testTitleIsBlank_異常系_titleが空白() {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, " ", "testContent", LocalDate.of(2025, 1, 1),
            false));
    assertEquals(ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testTitleIsTooShort_異常系_titleが0文字() {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, "", "testContent", LocalDate.of(2025, 1, 1), false));
    assertEquals(ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testTitleIsTooLong_異常系_titleの文字数オーバー() {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, "a".repeat(31), "testContent", LocalDate.of(2025, 1,
            1), false));
    assertEquals(ExceptionIdConstants.E_DIARY_VALUE_IS_OUT_OF_RANGE, e.getExceptionId());
  }

  @Test
  void testContentIsNull_異常系_contentがnull() {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, "testTitle", null, LocalDate.of(2025, 1, 1), false));
    assertEquals(ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testContentIsBlank_異常系_contentが空白() {
    DiaryValidationException e = assertThrows(DiaryValidationException.class,
        () -> new Diary(1L, 1L, "testTitle", " ", LocalDate.of(2025, 1, 1), false));
    assertEquals(ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testContentIsTooShort_異常系_contentが0文字() {
    DiaryValidationException e = assertThrows(DiaryValidationException.class,
        () -> new Diary(1L, 1L, "testTitle", "", LocalDate.of(2025, 1, 1), false));
    assertEquals(ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testContentIsTooLong_異常系_contentの文字数オーバー() {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, "testTitle", "a".repeat(1001), LocalDate.of(2025, 1,
            1), false));
    assertEquals(ExceptionIdConstants.E_DIARY_VALUE_IS_OUT_OF_RANGE, e.getExceptionId());
  }

  @Test
  void testDateIsNull_異常系_作成日時がnull() {
    DiaryValidationException e = assertThrows(DiaryValidationException.class,
        () -> new Diary(1L, 1L, "testTitle", "testContent", null, false));
    assertEquals(ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED, e.getExceptionId());
  }
}
