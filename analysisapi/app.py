"""Analysis API アプリケーションを起動するモジュールです。"""

from fastapi import FastAPI
import uvicorn
from presentation.controller import Controller
import settings


def main():
    """
    Analysis API アプリケーションを起動します。
    """
    app = FastAPI()
    app.include_router(Controller().router, prefix="/api", tags=["analysisapi"])
    uvicorn.run(app, host=settings.HOST, port=settings.PORT)


if __name__ == "__main__":
    main()
