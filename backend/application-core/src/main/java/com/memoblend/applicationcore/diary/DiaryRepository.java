package com.memoblend.applicationcore.diary;

import java.util.List;

/**
 * 日記のリポジトリのインターフェースです。
 */
public interface DiaryRepository {

  /**
   * 全ての日記を取得します。
   * 
   * @return 全ての日記。
   */
  List<Diary> findAll();

  /**
   * ID を指定して、日記を取得します。
   * 
   * @param id 日記の ID 。
   * @return 条件に合う日記。
   */
  Diary findById(long id);

  /**
   * ユーザー ID を指定して、日記を取得します。
   * 
   * @param userId ユーザー ID 。
   * @return 条件に合う日記のリスト。
   */
  List<Diary> findByUserId(long userId);

  /**
   * 日記を追加します。
   * 
   * @param diary 追加する日記。
   */
  Diary add(Diary diary);

  /**
   * ID を指定して、日記を削除します。
   * 
   * @param id 削除する日記の ID 。
   */
  long delete(long id);

  /**
   * 日記を更新します。
   * 
   * @param diary 更新する日記。
   */
  long update(Diary diary);
}
