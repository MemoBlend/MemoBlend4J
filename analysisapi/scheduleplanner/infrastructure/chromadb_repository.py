import chromadb

class ChromadbRepository():
  def __init__(self, 
                user_id: int,
                directory: str = "./infrastructure/chroma_db", 
                persist: bool = False, 
                ):
    """
    ChromadbRepository クラスのコンストラクタ。
    
    Args:
      user_id (int): ユーザーID。
      directory (str): データベースの保存先ディレクトリ。デフォルトは "./infrastructure/chroma_db"。
      persist (bool): データベースを保存するかどうかのフラグ。デフォルトは False。
    """
    if persist:
      # DBを保存する場合
      self.chroma_client = chromadb.PersistentClient(path=directory)
    else:
      # DBを保存しない場合
      self.chroma_client = chromadb.Client()
    self.collection = None
    self.user_id = user_id

  def load_collection(self) -> None:
    """
    コレクションをロードする関数。コレクションが存在しない場合は新たに作成する。

    Returns:
      None
    """     
    self.collection = self.chroma_client.create_collection(name=("user_id_"+str(self.user_id)), get_or_create=True)

  def add(self, diary_id: int, sentence: str) -> None:
    """
    sentence をベクトル化して、DBに追加する関数。

    Args:
      diary_id (int): 日記ID。
      sentence (str): 追加する文章。

    Returns:
      None
    """
    self.collection.add(
      ids=["diary_id_" + str(diary_id)], # 主キーに相当する。UUIDを使用する予定。
      documents=[sentence],      
      metadatas=[{"user_id": self.user_id, "diary_id": diary_id}], # メタデータにユーザーIDと日記IDを追加
    )

  def find_by_sentence(self, sentence: str) -> dict:
    """
    DBから sentence に類似するテキストを検索する関数。
    事前にDBがロードされていない場合はエラーを返す。

    Args:
      sentence (str): 検索する文章。
        
    Returns:
      dict: 検索結果。{"documents": [類似する文章]} の形式。

    Raises:
      ValueError: DBがロードされていない場合。
    """
    if self.collection is None:
      raise ValueError("DBがロードされていません。load_collection関数を呼び出してください。")
    results = self.collection.query(
      query_texts=[sentence],
      n_results=2
    )
    return results
