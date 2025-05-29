"""日記のアプリケーションサービスクラスです。"""

from logging import INFO
from applicationcore.collection_load_failed_exception import (
    CollectionLoadFailedException,
)
from infrastructure.diary_repository import DiaryRepository
from systemcommon.logger_config import LoggerConfig


# pylint: disable=too-few-public-methods
class DiaryApplicationService:
    """
    日記のアプリケーションサービスクラスです。
    """

    def __init__(self):
        self.diary_repository = DiaryRepository(enable_persistence=True)
        self.logger = LoggerConfig.get_logger(name=__name__, level=INFO)

    def add_text_to_vector_db(self, user_id: int, json_data: dict) -> None:
        """
        ベクトル DB に文章を追加します。
        Args:
            user_id (int): ユーザーID。
            json_data (dict): 追加するデータ。{"id": "1", "content": "sample text"}。

        Raises:
            ValueError: コレクションのロードまたは作成に失敗した場合。
        """
        self.logger.info("user_id: %sのデータをベクトルDBに追加します。", user_id)
        try:
            self.diary_repository.add(
                user_id=user_id, diary_id=json_data["id"], sentence=json_data["content"]
            )
        except ValueError as e:
            raise CollectionLoadFailedException(user_id) from e
