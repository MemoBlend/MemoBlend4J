package com.memoblend.web.controller.mapper.diary;

import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.web.controller.dto.diary.GetDiaryResponse;

/**
 * {@link Diary} を {@link GetDiaryResponse} に変換するクラスです。
 */
public class GetDiaryResponseMapper {

  // インスタンス化防止
  private GetDiaryResponseMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
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
