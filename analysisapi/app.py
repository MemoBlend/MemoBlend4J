"""Analysis API アプリケーションを起動するモジュールです。"""

from fastapi import FastAPI
import uvicorn
import settings
from web.controller import Controller


def main():
    """
    Analysis API アプリケーションを起動します。
    """
    app = FastAPI()
    app.include_router(Controller().router, prefix="/api", tags=["analysisapi"])
    uvicorn.run(app, host=settings.HOST, port=settings.PORT)
