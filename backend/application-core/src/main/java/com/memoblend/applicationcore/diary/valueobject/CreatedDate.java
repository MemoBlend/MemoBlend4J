package com.memoblend.applicationcore.diary.valueobject;

import java.time.LocalDate;
import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.applicationcore.diary.DiaryValidationException;
import lombok.Value;

/**
 * 作成日時を表す値オブジェクトです。
 */
@Value
public class CreatedDate {
  private final LocalDate value;

  private static final String VALUE_OBJECT_NAME = "作成日時";

  /**
   * {@link CreatedDate} クラスの新しいインスタンスを初期化します。
   * 
   * @param value 作成日時の値。
   * @throws DiaryValidationException 作成日時が不正な場合。
   */
  public CreatedDate(LocalDate value) throws DiaryValidationException {
    if (value == null) {
      throw new DiaryValidationException(
          ExceptionIdConstants.E_DIARY_FIELD_IS_REQUIRED,
          new String[] { String.valueOf(VALUE_OBJECT_NAME) },
          new String[] { String.valueOf(VALUE_OBJECT_NAME) });
    }
    this.value = value;
  }
}
