package com.memoblend.web.controller.dto.diary;

import java.util.List;
import com.memoblend.applicationcore.diary.Diary;
import lombok.Data;

/**
 * 日記のリスト取得レスポンスクラスです。
 */
@Data
public class GetDiariesResponse {
  private final List<Diary> diaries;
}
