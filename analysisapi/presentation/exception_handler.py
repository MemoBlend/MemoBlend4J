"""共通例外ハンドラーです。"""

from fastapi import HTTPException
from fastapi.responses import JSONResponse


class ExceptionHandlers:
    """
    共通例外ハンドラーです。
    """

    @staticmethod
    async def http_exception_handler(exc: HTTPException):
        """
        HTTPエラーを処理するための例外ハンドラーです。

        Args:
            exc (HTTPException): 発生したHTTP例外。

        Returns:
            JSONResponse: エラーレスポンス。
        """
        return JSONResponse(
            status_code=exc.status_code,
            content={"detail": exc.detail, "message": "HTTPエラーが発生しました"},
        )

    @staticmethod
    async def global_exception_handler(exc: Exception):
        """
        共通例外のグローバルハンドラーです。

        Args:
            exc (Exception): 発生した例外。

        Returns:
            JSONResponse: エラーレスポンス。
        """
        return JSONResponse(
            status_code=500,
            content={"detail": str(exc), "message": "予期しないエラーが発生しました"},
        )
