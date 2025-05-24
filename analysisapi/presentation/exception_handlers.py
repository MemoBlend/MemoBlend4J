"""共通例外ハンドラーです。"""

from fastapi import HTTPException
from fastapi.responses import JSONResponse


class ExceptionHandlers:
    """
    共通例外ハンドラーです。
    """

    @staticmethod
    async def http_exception_handler(exception: HTTPException):
        """
        HTTPエラーを処理するための例外ハンドラーです。

        Args:
            exception (HTTPException): 発生したHTTP例外。

        Returns:
            JSONResponse: エラーレスポンス。
        """
        return JSONResponse(
            status_code=exception.status_code,
            content={"detail": exception.detail, "message": "HTTPエラーが発生しました"},
        )

    @staticmethod
    async def global_exception_handler(exception: Exception):
        """
        共通例外の例外ハンドラーです。

        Args:
            exception (Exception): 発生した例外。

        Returns:
            JSONResponse: エラーレスポンス。
        """
        return JSONResponse(
            status_code=500,
            content={
                "detail": str(exception),
                "message": "予期しないエラーが発生しました",
            },
        )
