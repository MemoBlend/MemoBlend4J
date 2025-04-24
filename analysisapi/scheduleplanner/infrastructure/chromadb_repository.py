import chromadb
from analysisapi.scheduleplanner.applicationcore.abstractrepository.abstract_chromadb_repository import AbstractChromadbRepository

class ChromadbRepository(AbstractChromadbRepository):
  def __init__(self, 
                user_id: int,
                directory: str = "./chroma_db", 
                persist: bool = False, 
                ):
    """
    コンストラクタ
    
    :param user_id: ユーザーID
    :param directory: DBの保存先ディレクトリ
    :param persist: DBを保存するかどうか
    """
    if persist:
      # DBを保存する場合
      self.chroma_client = chromadb.PersistentClient(path=directory)
    else:
      # DBを保存しない場合
      self.chroma_client = chromadb.Client()
    self.collection = None
    self.user_id = user_id

  def load_collection(self):
    """
    コレクションをロードするメソッド。コレクションが存在しない場合は新たに作成する。
    """     
    self.collection = self.chroma_client.create_collection(name=("user_id_"+str(self.user_id)), get_or_create=True)

  def add(self, diary_id: int, sentence: str) -> None:
    """
    sentence をベクトル化して、コレクションに追加するメソッド。

    :param diary_id: 日記のID
    :param sentence: 追加する文章
    """
    self.collection.add(
      ids=["diary_id_" + str(diary_id)], # 主キーに相当する。UUIDを使用する予定。
      documents=[sentence],      
      metadatas=[{"user_id": self.user_id, "diary_id": diary_id}], # メタデータにユーザーIDと日記IDを追加
    )

  def find_by_sentence(self, sentence: str) -> dict:
    """
    コレクションから sentence に類似するテキストを検索するメソッド。
    事前にコレクションがロードされていない場合はエラーを返す。

    :param sentence: 類似検索する文章または単語
    :return: 検索結果
    :rtype: dict
    """
    if self.collection is None:
      raise ValueError("コレクションがロードされていません。load_collectionメソッドを呼び出してください。")
    results = self.collection.query(
      query_texts=[sentence],
      n_results=2
    )
    return results
