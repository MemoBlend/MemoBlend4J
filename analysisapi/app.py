"""Analysis API アプリケーションを起動するモジュールです。"""

from fastapi import FastAPI
import uvicorn
from presentation.controller import Controller
from presentation.exception_handlers import ExceptionHandlers
import settings
from systemcommon.business_exception import BusinessException
from systemcommon.system_exception import SystemException


app = FastAPI()
app.include_router(Controller().router, prefix="/api", tags=["analysisapi"])
app.add_exception_handler(
    BusinessException, ExceptionHandlers.business_exception_handler
)
app.add_exception_handler(SystemException, ExceptionHandlers.system_exception_handler)
app.add_exception_handler(Exception, ExceptionHandlers.global_exception_handler)


if __name__ == "__main__":
    uvicorn.run(app, host=settings.HOST, port=settings.PORT)
