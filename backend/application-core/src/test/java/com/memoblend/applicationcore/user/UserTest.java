package com.memoblend.applicationcore.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { " ", "" })
  void testNameIsNull_異常系_nameが空(String name) {
    UserValidationException e = assertThrows(UserValidationException.class,
        () -> new User(1L, name, false));
    assertEquals(ExceptionIdConstants.E_USER_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testNameIsTooLong_異常系_nameの文字数オーバー() {
    UserValidationException e = assertThrows(UserValidationException.class,
        () -> new User(1L, "a".repeat(16), false));
    assertEquals(ExceptionIdConstants.E_USER_VALUE_IS_OUT_OF_RANGE, e.getExceptionId());
  }
}
