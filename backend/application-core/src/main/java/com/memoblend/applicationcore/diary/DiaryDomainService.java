package com.memoblend.applicationcore.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

/**
 * 日記のドメインサービスです。
 */
@AllArgsConstructor
@Component
public class DiaryDomainService {
  @Autowired
  private DiaryRepository diaryRepository;

  /**
   * 日記が存在するかどうかを判定します。
   * 
   * @param id 日記の ID 。
   * @return 日記が存在する場合は true、存在しない場合は false。
   */
  public boolean isExistDiary(long id) {
    return diaryRepository.findById(id) != null;
  }
}
