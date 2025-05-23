"""AnalysisAPIのコントローラークラスです。"""

from fastapi import APIRouter, HTTPException
import httpx
from applicationcore.diary_application_service import DiaryApplicationService
from applicationcore.client_application_service import ClientApplicationService
from presentation.presentation_constants import PresentationConstants


class Controller:
    """
    AnalysisAPIのコントローラークラスです。
    """

    def __init__(self):
        self.router = APIRouter()
        self.router.add_api_route(
            path="/diary/{user_id}/{diary_id}",
            endpoint=self.get_diary_add_db,
            methods=["GET"],
        )
        self.router.add_api_route(
            path="/schedule/{user_id}",
            endpoint=self.get_schedule_suggestion,
            methods=["GET"],
        )
        self.diary_application_service = DiaryApplicationService()
        self.client_application_service = ClientApplicationService()

    def get_diary_add_db(self, user_id: int, diary_id: int) -> dict:
        """
        指定idのユーザーの指定idの日記を取得し、ベクトルDBに追加します。

        Args:
            user_id (int): ユーザーID。
            diary_id (int): 日記ID。

        Returns:
            dict: 日記が正常にDBに追加されたことを示すメッセージ。

        Raises:
            HTTPException: サーバーからのHTTPエラー応答が発生した場合。
            RequestException: 通信中にエラーが発生した場合。
            Exception: その他の予期しないエラー。
        """
        url = f"{PresentationConstants.DIARY_GET_URL}/{diary_id}"

        try:
            with httpx.Client() as client:
                response = client.get(url)
                response.raise_for_status()
        except httpx.HTTPStatusError as e:
            raise HTTPException(
                status_code=e.response.status_code,
                detail=f"HTTPステータスエラー: {e.response.status_code} - {e.response.text}",
            ) from e
        except httpx.RequestError as e:
            raise HTTPException(
                status_code=502,
                detail=f"ネットワークエラー: {e.__class__.__name__} - {str(e)}",
            ) from e
        except Exception as e:
            raise HTTPException(
                status_code=500, detail=f"その他のエラーが発生しました: {str(e)}"
            ) from e

        try:
            self.diary_application_service.add_text_to_vector_db(
                user_id, response.json()
            )
        except (KeyError, TypeError, ValueError) as e:
            raise HTTPException(status_code=400, detail=f"入力エラー: {e}") from e
        except ConnectionError as e:
            raise HTTPException(status_code=502, detail=f"接続エラー: {e}") from e
        except Exception as e:
            raise HTTPException(
                status_code=500, detail=f"その他の予期しないエラー: {e}"
            ) from e

        return {"message": "日記が正常にDBに追加されました"}

    def get_schedule_suggestion(self, user_id: int) -> str:
        """
        過去の日記を分析して、明日の予定の提案を取得します。

        Args:
            user_id (int): ユーザーID。

        Returns:
            str: 明日の予定の提案。
        """
        location = {"latitude": 35.7001076, "longitude": 139.9855455}

        try:
            response = self.client_application_service.get_llm_output(location, user_id)
            return response["choices"][0]["message"]["content"]

        except Exception as e:
            raise HTTPException(
                status_code=500, detail=f"予定立案に失敗しました: {str(e)}"
            ) from e
