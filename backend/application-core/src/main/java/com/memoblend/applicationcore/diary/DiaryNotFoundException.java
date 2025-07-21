package com.memoblend.applicationcore.diary;

import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.systemcommon.exception.LogicException;

/**
 * 日記が見つからない場合の例外です。
 */
public class DiaryNotFoundException extends LogicException {

  /**
   * ID を指定して、 {@link DiaryNotFoundException} クラスのインスタンスを初期化します。
   * 
   * @param id 日記の ID 。
   */
  public DiaryNotFoundException(long id) {
    super(null, ExceptionIdConstants.E_DIARY_NOT_FOUND, new String[] { String.valueOf(id) },
        new String[] { String.valueOf(id) });
  }
}
