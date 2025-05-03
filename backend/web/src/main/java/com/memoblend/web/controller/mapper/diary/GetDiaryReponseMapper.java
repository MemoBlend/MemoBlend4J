package com.memoblend.web.controller.mapper.diary;

import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.web.controller.dto.diary.GetDiaryResponse;

/**
 * {@link Diary} を {@link GetDiaryResponse} に変換するクラスです。
 */
public class GetDiaryReponseMapper {

  // Prevent instantiation
  private GetDiaryReponseMapper() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * {@link Diary} を {@link GetDiaryResponse} に変換します。
   * 
   * @param diary 日記。
   * @return {@link GetDiaryResponse} のインスタンス。
   */
  public static GetDiaryResponse convert(Diary diary) {
    return new GetDiaryResponse(
        diary.getId(),
        diary.getUserId(),
        diary.getTitle(),
        diary.getContent(),
        diary.getCreatedDate());
  }
}
