"""AnalysisAPIのコントローラークラスです。"""

from logging import INFO
from fastapi import APIRouter
from fastapi.responses import JSONResponse
from applicationcore.collection_load_failed_exception import (
    CollectionLoadFailedException,
)
from applicationcore.diary_application_service import DiaryApplicationService
from applicationcore.client_application_service import ClientApplicationService
from presentation.dto.post_diary_request import PostDiaryRequest
from systemcommon.logger_config import LoggerConfig


class Controller:
    """
    AnalysisAPIのコントローラークラスです。
    """

    def __init__(self):
        self.router = APIRouter()
        self.router.add_api_route(
            path="/diary/{user_id}",
            endpoint=self.get_diary_add_db,
            methods=["POST"],
        )
        self.router.add_api_route(
            path="/schedule/{user_id}",
            endpoint=self.get_schedule_suggestion,
            methods=["GET"],
        )
        self.diary_application_service = DiaryApplicationService()
        self.client_application_service = ClientApplicationService()
        self.logger = LoggerConfig.get_logger(name=__name__, level=INFO)

    def get_diary_add_db(
        self, user_id: int, post_diary_request: PostDiaryRequest
    ) -> JSONResponse:
        """
        指定idのユーザーの日記を取得し、ベクトルDBに追加します。

        Args:
            user_id (int): ユーザーID。
            post_diary_request (PostDiaryRequest): 日記をベクトルDBに登録するためのリクエストDTO。

        Returns:
            JSONResponse: 日記が正常にDBに追加されたことを示すメッセージ。

        Raises:
            CollectionLoadFailedException: コレクションのロードまたは作成に失敗した場合。
        """
        diary_text = post_diary_request.diary_text
        diary_id = post_diary_request.id

        try:
            self.diary_application_service.add_text_to_vector_db(
                user_id, {"id": diary_id, "text": diary_text}
            )
        except CollectionLoadFailedException as e:
            self.logger.error(e.message)
            return JSONResponse(status_code=500, content={"error": e.message})

        return JSONResponse(
            status_code=200,
            content={
                "message": "日記が正常にDBに追加されました",
                "diary_id": diary_id,
                "user_id": user_id,
                "diary_text": diary_text,
            },
        )

    def get_schedule_suggestion(self, user_id: int) -> JSONResponse:
        """
        過去の日記を分析して、明日の予定の提案を取得します。

        Args:
            user_id (int): ユーザーID。

        Returns:
            JSONResponse: 明日の予定の提案を含むJSONレスポンス。
        """
        location = {"latitude": 35.7001076, "longitude": 139.9855455}

        response = self.client_application_service.get_llm_output(location, user_id)
        return JSONResponse(
            status_code=200,
            content={
                "message": "明日の予定の提案を取得しました",
                "suggestion": response.choices[0].message.content,
            },
        )
