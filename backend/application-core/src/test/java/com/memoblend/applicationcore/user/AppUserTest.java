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

import com.memoblend.applicationcore.appuser.AppUser;
import com.memoblend.applicationcore.appuser.AppUserValidationException;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;

/**
 * ユーザーのドメインモデルのテストクラスです。
 */
@ExtendWith(SpringExtension.class)
class AppUserTest {

  @Test
  void testNoError_正常系_ユーザーを作成できる() {
    // Act
    assertDoesNotThrow(() -> new AppUser(1L, "testName", false, "auth_1"));
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { " ", "" })
  void testNameIsNull_異常系_nameが空(String name) {
    AppUserValidationException e = assertThrows(AppUserValidationException.class,
        () -> new AppUser(1L, name, false, "auth_1"));
    assertEquals(ExceptionIdConstants.E_USER_FIELD_IS_REQUIRED, e.getExceptionId());
  }

  @Test
  void testNameIsTooLong_異常系_nameの文字数オーバー() {
    AppUserValidationException e = assertThrows(AppUserValidationException.class,
        () -> new AppUser(1L, "a".repeat(16), false, "auth_1"));
    assertEquals(ExceptionIdConstants.E_USER_VALUE_IS_OUT_OF_RANGE, e.getExceptionId());
  }
}
