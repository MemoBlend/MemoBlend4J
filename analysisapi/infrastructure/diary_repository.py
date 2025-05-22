"""日記のリポジトリクラスです。"""

import chromadb

from infrastructure.infrastructure_constants import InfrastructureConstants


class DiaryRepository:
    """
    日記のリポジトリクラスです。
    ChromaDB を使用して、ユーザーごとの日記を管理します。
    """

    def __init__(
        self,
        enable_persistence: bool = False,
    ):
        """
        Args:
            enable_persistence (bool): データベースの永続化を有効にするかどうか。デフォルトは False。
        """
        if enable_persistence:
            self.chroma_db_client = chromadb.PersistentClient(
                path=InfrastructureConstants.DIARY_DB_PATH
            )
        else:
            self.chroma_db_client = chromadb.Client()
        self.collection = None

    def add(self, user_id: int, diary_id: int, sentence: str):
        """
        文章をベクトルDBに追加します。

        Args:
            user_id (int): ユーザーID。
            diary_id (int): 日記ID。
            sentence (str): 追加する文章。

        Raises:
            ValueError: 日記のコレクションのロードまたは作成に失敗した場合。
        """

        try:
            self._load_collection(user_id)
        except ValueError as e:
            raise e
        self.collection.add(
            # 主キーに相当する。UUIDを使用する予定。
            ids=[f"diary_id_{diary_id}"],
            documents=[sentence],
            # メタデータにユーザーIDと日記IDを追加
            metadatas=[{"user_id": user_id, "diary_id": diary_id}],
        )

    def find_by_sentence(self, user_id: int, sentence: str, n_results: int = 2) -> dict:
        """
        データベースから sentence に類似するテキストを検索します。

        Args:
            user_id (int): ユーザーID。
            sentence (str): 検索する文章。
            n_results (int): 返却する類似検索結果の最大件数（デフォルト: 2）。

        Returns:
            dict: 検索結果。{"documents": [類似する文章]} の形式です。

        Raises:
            ValueError: コレクションのロードまたは作成に失敗した場合。
        """
        try:
            self._load_collection(user_id)
        except ValueError as e:
            raise e

        return self.collection.query(query_texts=[sentence], n_results=n_results)

    def _load_collection(self, user_id: int):
        """
        コレクションをロードします。コレクションが存在しない場合は新たに作成します。
        コレクション名は、"user_id_{user_id}" です。

        Args:
            user_id (int): ユーザーID。
        Raises:
            ValueError: コレクションのロードまたは作成に失敗した場合。
        """
        try:
            self.collection = self.chroma_db_client.create_collection(
                name=f"user_id_{user_id}", get_or_create=True
            )
        except ValueError as e:
            raise ValueError("コレクションのロードまたは作成に失敗しました。") from e
