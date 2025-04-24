from abc import ABC, abstractmethod

class AbstractChromadbRepository(ABC):
  """
  Chromadbとのやりとりを行うリポジトリの抽象クラス。
  """

  @abstractmethod
  def load_collection(self) -> None:
    """
    コレクションをロード、または作成するメソッド。
    """
    pass

  @abstractmethod
  def add(self, id: int, text: str) -> None:
    """
    テキストを追加するメソッド。

    :param id: テキストのID
    :param text: 追加するテキスト
    """
    pass
  
  @abstractmethod
  def query(self, target_text: str) -> dict:
    """
    類似するテキストを検索するメソッド。

    :param target_text: 検索するテキスト
    :return: 検索結果の辞書
    """
    pass