package com.memoblend.applicationcore.diary.valueobject;

import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.applicationcore.diary.DiaryValidationException;
import lombok.Value;

/**
 * コンテンツを表す値オブジェクトです。
 */
@Value
public class Content {
  private static final String VALUE_OBJECT_NAME = "コンテンツ";
  private final String value;

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
          new String[] { VALUE_OBJECT_NAME },
          new String[] { VALUE_OBJECT_NAME });
    }
    if (value.length() <= 1 || value.length() >= 1000) {
      throw new DiaryValidationException(
          ExceptionIdConstants.E_DIARY_VALUE_IS_OUT_OF_RANGE,
          new String[] { VALUE_OBJECT_NAME, "1", "1000" },
          new String[] { VALUE_OBJECT_NAME, "1", "1000" });
    }
    this.value = value;
  }
}
