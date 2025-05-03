package com.memoblend.applicationcore.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;

/**
 * ユーザーのドメインモデルのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
class UserTest {

  @Test
  void testNoError_正常系_ユーザーを作成できる() {
    // Act
    assertDoesNotThrow(() -> new User(1L, "testName", false));
  }

  @Test
  void testNameIsNull_異常系_nameがnull() {
    UserValidationException e = assertThrows(UserValidationException.class,
        () -> new User(1L, null, false));
    assertEquals(ExceptionIdConstants.E_USER_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testNameIsBlank_異常系_nameが空白() {
    UserValidationException e = assertThrows(UserValidationException.class,
        () -> new User(1L, " ", false));
    assertEquals(ExceptionIdConstants.E_USER_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testNameIsTooShort_異常系_nameが0文字() {
    UserValidationException e = assertThrows(UserValidationException.class,
        () -> new User(1L, "", false));
    assertEquals(ExceptionIdConstants.E_USER_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testNameIsTooLong_異常系_nameの文字数オーバー() {
    UserValidationException e = assertThrows(UserValidationException.class,
        () -> new User(1L, "a".repeat(16), false));
    assertEquals(ExceptionIdConstants.E_USER_VALUE_IS_OUT_OF_RANGE, e.getExceptionId());
  }
}
