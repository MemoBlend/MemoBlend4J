package com.memoblend.applicationcore.diary.valueobject;

import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.applicationcore.diary.DiaryValidationException;
import lombok.Value;

/**
 * タイトルを表す値オブジェクトです。
 */
@Value
public class Title {
  private static final String VALUE_OBJECT_NAME = "タイトル";
  private final String value;
  /**
   * {@link Title} クラスの新しいインスタンスを初期化します。
   * 
   * @param value タイトルの値。
   * @throws DiaryValidationException タイトルが不正な場合。
   */
  public Title(String value) throws DiaryValidationException {
    if (value == null || value.isEmpty() || value.isBlank()) {
      throw new DiaryValidationException(
          ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED,
          new String[] { String.valueOf(VALUE_OBJECT_NAME) },
          new String[] { String.valueOf(VALUE_OBJECT_NAME) });
    }
    if (value.length() <= 1 || value.length() >= 30) {
      throw new DiaryValidationException(
          ExceptionIdConstants.E_DIARY_VALUE_IS_OUT_OF_RANGE,
          new String[] { String.valueOf(VALUE_OBJECT_NAME), "1", "30" },
          new String[] { String.valueOf(VALUE_OBJECT_NAME), "1", "30" });
    }
    this.value = value;
  }
}
