"""インフラストラクチャ層の定数を定義するクラスです。"""


# pylint: disable=too-few-public-methods
class InfrastructureConstants:
    """インフラストラクチャ層の定数を定義するクラスです。"""

    # 日記のベクトル DB のパスです。
    # 日記のリポジトリクラスからの相対パスで定義します。
    DIARY_DB_PATH: str = "./infrastructure/diary"

    CHUNK_SIZE: int = 40
    CHUNK_OVERLAP: int = 10
