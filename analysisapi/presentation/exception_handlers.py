"""共通例外ハンドラーです。"""

import logging
import traceback
from fastapi import Request
from fastapi.responses import JSONResponse
from systemcommon.logger_config import LoggerConfig
from systemcommon.system_exception import SystemException


class ExceptionHandlers:
    """
    共通例外ハンドラーです。
    """

    logger = LoggerConfig.get_logger(name=__name__, level=logging.INFO)

    @staticmethod
    async def business_exception_handler(_request: Request, exception: SystemException):
        """
        ビジネス例外を処理するための例外ハンドラーです。

        Args:
            request (Request): リクエストオブジェクト。
            exception (SystemException): 発生したビジネス例外。

        Returns:
            JSONResponse: エラーレスポンス。
        """

        ExceptionHandlers.logger.error(
            "システムエラーが発生しました: %s\n%s", exception, traceback.format_exc()
        )
        return JSONResponse(
            status_code=500,
            content={
                "message": "システムエラーが発生しました",
            },
        )

    @staticmethod
    async def system_exception_handler(_request: Request, exception: SystemException):
        """
        システム例外を処理するための例外ハンドラーです。

        Args:
            request (Request): リクエストオブジェクト。
            exception (SystemException): 発生したシステム例外。

        Returns:
            JSONResponse: エラーレスポンス。
        """

        ExceptionHandlers.logger.error(
            "システムエラーが発生しました: %s\n%s", exception, traceback.format_exc()
        )
        return JSONResponse(
            status_code=500,
            content={
                "message": "システムエラーが発生しました",
            },
        )

    @staticmethod
    async def global_exception_handler(_request: Request, exception: Exception):
        """
        共通例外の例外ハンドラーです。

        Args:
            request (Request): リクエストオブジェクト。
            exception (Exception): 発生した例外。

        Returns:
            JSONResponse: エラーレスポンス。
        """

        ExceptionHandlers.logger.error(
            "システムエラーが発生しました: %s\n%s", exception, traceback.format_exc()
        )
        return JSONResponse(
            status_code=500,
            content={
                "message": "システムエラーが発生しました",
            },
        )
