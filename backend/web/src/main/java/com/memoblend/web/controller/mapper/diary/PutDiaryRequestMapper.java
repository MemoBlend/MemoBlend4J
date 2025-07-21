package com.memoblend.web.controller.mapper.diary;

import com.memoblend.applicationcore.diary.Diary;
import com.memoblend.applicationcore.diary.DiaryValidationException;
import com.memoblend.web.controller.dto.diary.PutDiaryRequest;

/**
 * {@link PutDiaryRequest} から {@link Diary} への変換を行うクラスです。
 */
public class PutDiaryRequestMapper {

  /**
   * {@link PutDiaryRequest} から {@link Diary} へ変換します。
   * 
   * @param request 日記の更新リクエスト。
   * @return 日記。
   * @throws DiaryValidationException 日記が不正な場合。
   */
  public static Diary convert(PutDiaryRequest request) throws DiaryValidationException {
    return new Diary(
        request.getId(),
        request.getTitle(),
        request.getContent(),
        request.getCreatedDate(),
        false,
        request.getUserId());
  }

  // インスタンス化防止
  private PutDiaryRequestMapper() {
    throw new UnsupportedOperationException("ユーティリティクラスのためインスタンス化できません");
  }
}
