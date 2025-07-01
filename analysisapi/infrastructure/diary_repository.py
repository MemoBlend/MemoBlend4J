"""日記のリポジトリクラスです。"""

import chromadb
from infrastructure.diary_repository_failed_exception import DiaryRepositoryFailedException
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

        """
        self._load_collection(user_id)
        chunks = self._chunk_text(sentence)
        for i, chunk in enumerate(chunks, 1):
            self.collection.add(
                ids=[f"{diary_id}_{i}"],
                documents=[chunk],
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

        """
        self._load_collection(user_id)

        return self.collection.query(query_texts=[sentence], n_results=n_results)

    def _load_collection(self, user_id: int):
        """
        コレクションをロードします。コレクションが存在しない場合は新たに作成します。
        コレクション名は、"user_id_{user_id}" です。

        Args:
            user_id (int): ユーザーID。
        """
        self.collection = self.chroma_db_client.create_collection(
            name=f"user_id_{user_id}", get_or_create=True
        )

    def _chunk_text(self, text: str) -> list[str]:
        """
        text を指定の CHUNK_SIZE 文字ごとに分割し、
        各チャンク間を CHUNK_OVERLAP 文字分だけ重複させて返す。

        Args:
            text (str): 分割するテキスト。

        Returns:
            list[str]: 分割されたテキストのリスト。

        Raises:
            ValueError: CHUNK_OVERLAP が CHUNK_SIZE 以上の場合。
        """
        if InfrastructureConstants.CHUNK_OVERLAP >= InfrastructureConstants.CHUNK_SIZE:
            raise DiaryRepositoryFailedException()

        chunks: list[str] = []
        start = 0
        text_length = len(text)

        while start < text_length:
            end = min(start + InfrastructureConstants.CHUNK_SIZE, text_length)
            if end < text_length:
                best_break = end
                for pivot in ["。", "！", "？", "\n"]:
                    idx = text.rfind(pivot, start, end)
                    if idx != -1:
                        tmp_break = idx + 1
                        # チャンクが小さすぎないか確認
                        if tmp_break > start + InfrastructureConstants.CHUNK_SIZE // 2:
                            best_break = tmp_break
                            break
                end = best_break

            chunk = text[start:end].strip()
            if chunk:
                print(f"Chunk created: {chunk}")
                chunks.append(chunk)

            if end >= text_length:
                break

            start = max(end - InfrastructureConstants.CHUNK_OVERLAP, start + 1)

        return chunks