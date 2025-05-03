from abc import ABC, abstractmethod

class AbstractChromadbRepository(ABC):
  """
  Chromadbとのやりとりを行うリポジトリの抽象クラス。
  """

  @abstractmethod
  def load_collection(self) -> None:
    """
    コレクションをロードするメソッド。コレクションが存在しない場合は新たに作成する。
    """     
    pass

  @abstractmethod
  def add(self, diary_id: int, sentence: str) -> None:
    """
    sentence をベクトル化して、コレクションに追加するメソッド。

    :param diary_id: 日記のID
    :param sentence: 追加する文章
    """
    pass
  
  @abstractmethod
  def find_by_sentence(self, sentence: str) -> dict:
    """
    コレクションから sentence に類似するテキストを検索するメソッド。
    事前にコレクションがロードされていない場合はエラーを返す。

    :param sentence: 類似検索する文章または単語
    :return: 検索結果
    :rtype: dict
    """
    pass