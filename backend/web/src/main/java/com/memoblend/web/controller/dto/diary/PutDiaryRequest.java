package com.memoblend.web.controller.dto.diary;

import java.time.LocalDate;
import lombok.Data;

/**
 * 日記の更新リクエストクラスです。
 */
@Data
public class PutDiaryRequest {
  private final long id;
  private final long userId;
  private final String title;
  private final String content;
  private final LocalDate createdDate;
}
