package com.memoblend.web.controller.dto.diary;

import java.time.LocalDate;
import lombok.Data;

/**
 * 日記の投稿リクエストクラスです。
 */
@Data
public class PostDiaryRequest {
  private final long userId;
  private final String title;
  private final String content;
  private final LocalDate createdDate;
}
