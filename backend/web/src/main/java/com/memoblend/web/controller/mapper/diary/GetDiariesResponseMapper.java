package com.memoblend.web.controller.mapper.diary;

import java.util.List;
import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.web.controller.dto.diary.GetDiariesResponse;

/**
 * {@link Diary} のリストを {@link GetDiariesResponse} に変換するクラスです。
 */
public class GetDiariesResponseMapper {

  /**
   * {@link Diary} のリストを {@link GetDiariesResponse} に変換します。
   * 
   * @param diaries 日記のリスト。
   * @return {@link GetDiariesResponse} のインスタンス。
   */
  public static GetDiariesResponse convert(List<Diary> diaries) {
    return new GetDiariesResponse(diaries);
  }

  // インスタンス化防止
  private GetDiariesResponseMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
