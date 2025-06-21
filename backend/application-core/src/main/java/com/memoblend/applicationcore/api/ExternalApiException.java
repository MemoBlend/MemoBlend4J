package com.memoblend.applicationcore.api;

import com.memoblend.applicationcore.constant.ExceptionIdConstants;
import com.memoblend.systemcommon.exception.LogicException;

/**
 * 外部 API の呼び出しに失敗した場合にスローされる例外クラスです。
 */
public class ExternalApiException extends LogicException {

  /**
   * 外部 API 名を指定して、 {@link ExternalApiException} クラスのインスタンスを初期化します。
   * 
   * @param name 外部 API の名前。
   */
  public ExternalApiException(String name) {
    super(null, ExceptionIdConstants.E_EXTERNAL_API_ERROR, new String[] { name }, new String[] { name });
  }

}
