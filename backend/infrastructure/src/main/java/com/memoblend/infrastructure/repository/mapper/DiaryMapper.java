package com.memoblend.infrastructure.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.memoblend.applicationcore.diary.Diary;

/**
 * 日記の OR マッパークラスです。
 */
@Mapper
public interface DiaryMapper {

  /**
   * 日記を全件取得します。
   * 
   * @return 全ての日記。
   */
  public List<Diary> findAll();

  /**
   * ユーザー ID を指定して、日記を取得します。
   * 
   * @param userId ユーザー ID 。
   * @return 条件に合う日記のリスト。
   */
  public List<Diary> findByUserId(long userId);

  /**
   * ID を指定して、日記を取得します。
   * 
   * @param id 日記の ID 。
   * @return 条件に合う日記。
   */
  public Diary findById(long id);

  /**
   * 日記を追加します。
   * 
   * @param diary 追加する日記。
   */
  public long add(Diary diary);

  /**
   * ID を指定して、日記を削除します。
   * 
   * @param id 削除する日記の ID 。
   */
  public long delete(long id);

  /**
   * 日記を更新します。
   * 
   * @param diary 更新する日記。
   */
  public long update(Diary diary);
}
