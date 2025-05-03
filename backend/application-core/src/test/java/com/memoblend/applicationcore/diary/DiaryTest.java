package com.memoblend.applicationcore.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;

/**
 * Diary クラスのテストクラスです。
 */
class DiaryTest {

  @Test
  void testNoError_正常系_日記を作成できる() {
    assertDoesNotThrow(() -> new Diary(1L, 1L, "testTitle", "testContent", LocalDate.of(2025, 1, 1), false));
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { " ", "" })
  void testTitleIsNull_異常系_titleが空(String title) {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, title, "testContent", LocalDate.of(2025, 1, 1), false));
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

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { " ", "" })
  void testContentIsNull_異常系_contentが空(String content) {
    DiaryValidationException e = assertThrows(
        DiaryValidationException.class,
        () -> new Diary(1L, 1L, "testTitle", content, LocalDate.of(2025, 1, 1), false));
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
