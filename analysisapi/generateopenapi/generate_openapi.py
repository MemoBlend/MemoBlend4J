"""OpenAPI 仕様書を生成するモジュールです。"""

import json
import os
from fastapi import FastAPI
from generateopenapi.constants import Constants
import settings
from presentation.controller import Controller


def main():
    """
    OpenAPI仕様書を生成します。
    """
    app = FastAPI(
        title=settings.PROJECT_NAME,
        description=settings.PROJECT_DESCRIPTION,
        version=settings.PROJECT_VERSION,
    )
    app.include_router(Controller().router, prefix="/api", tags=["analysisapi"])
    os.makedirs(os.path.dirname(Constants.API_SPECIFICATION_FILE_PATH), exist_ok=True)
    with open(Constants.API_SPECIFICATION_FILE_PATH, "w", encoding="utf-8") as f:
        api_spec = app.openapi()
        f.write(json.dumps(api_spec, indent=2, ensure_ascii=False))
