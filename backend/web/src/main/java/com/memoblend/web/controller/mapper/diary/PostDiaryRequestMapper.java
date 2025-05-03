package com.memoblend.web.controller.mapper.diary;

import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryValidationException;
import com.memoblend.web.controller.dto.diary.PostDiaryRequest;

/**
 * {@link PostDiaryRequest} を {@link Diary} に変換するクラスです。
 */
public class PostDiaryRequestMapper {

  // Prevent instantiation
  private PostDiaryRequestMapper() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * {@link PostDiaryRequest} を {@link Diary} に変換します。
   * 
   * @param request リクエスト。
   * @return 日記。
   * @throws DiaryValidationException 日記が不正な場合。
   */
  public static Diary convert(PostDiaryRequest request) throws DiaryValidationException {
    return new Diary(
        0,
        request.getUserId(),
        request.getTitle(),
        request.getContent(),
        request.getCreatedDate(),
        false);
  }
}
