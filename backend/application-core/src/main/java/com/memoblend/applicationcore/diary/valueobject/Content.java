package com.memoblend.applicationcore.diary.valueobject;

import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.applicationcore.diary.DiaryValidationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * コンテンツを表す値オブジェクトです。
 */
@Getter
@EqualsAndHashCode
public class Content {
  private final String value;

  private final String ValueObjectName = "コンテンツ";

  /**
   * {@link Content} クラスの新しいインスタンスを初期化します。
   * 
   * @param value コンテンツの値。
   * @throws DiaryValidationException コンテンツが不正な場合。
   */
  public Content(String value) throws DiaryValidationException {
    if (value == null || value.isEmpty() || value.isBlank()) {
      throw new DiaryValidationException(
          ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED,
          new String[] { String.valueOf(ValueObjectName) },
          new String[] { String.valueOf(ValueObjectName) });
    }
    if (value.length() <= 1 || value.length() >= 1000) {
      throw new DiaryValidationException(
          ExceptionIdConstants.E_DIARY_VALUE_IS_OUT_OF_RANGE,
          new String[] { String.valueOf(ValueObjectName), "1", "1000" },
          new String[] { String.valueOf(ValueObjectName), "1", "1000" });
    }
    this.value = value;
  }
}
