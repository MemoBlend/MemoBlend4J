package com.memoblend.web.controller.mapper.diary;

import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.web.controller.dto.diary.GetDiaryResponse;

/**
 * {@link Diary} を {@link GetDiaryResponse} に変換するクラスです。
 */
public class GetDiaryReponseMapper {

  /**
   * {@link Diary} を {@link GetDiaryResponse} に変換します。
   * 
   * @param diary 日記。
   * @return {@link GetDiaryResponse} のインスタンス。
   */
  public static GetDiaryResponse convert(Diary diary) {
    GetDiaryResponse response = new GetDiaryResponse(
        diary.getId(),
        diary.getUserId(),
        diary.getTitle(),
        diary.getContent(),
        diary.getDate());
    return response;
  }
}
