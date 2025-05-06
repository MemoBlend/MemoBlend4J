import chromadb

class ChromadbRepository():
  """
  ベクトルDBのリポジトリクラスです。
  ChromaDBを使用して、ユーザーごとのコレクションを管理します。
  """
  def __init__(self, 
                user_id: int,
                directory: str = "./scheduleplanner/infrastructure/chroma_db", 
                persist: bool = False, 
                ):
    """    
    Args:
      user_id (int): ユーザーID。
      directory (str): データベースの保存先ディレクトリ。デフォルトは "./infrastructure/chroma_db"。
      persist (bool): データベースを保存するかどうかのフラグ。デフォルトは False。
    """
    # DBの永続化を行うかどうかで条件分岐
    if persist:
      self.chroma_client = chromadb.PersistentClient(path=directory)
    else:
      self.chroma_client = chromadb.Client()

    self.user_id = user_id
    self.collection = None

  def load_collection(self) -> None:
    """
    コレクションをロードします。コレクションが存在しない場合は新たに作成します。
    コレクション名は、"user_id_{user_id}" 形式です。

    Returns:
      None
    """     
    self.collection = self.chroma_client.create_collection(
      name=f"user_id_{self.user_id}", get_or_create=True
    )

  def add(self, diary_id: int, sentence: str) -> None:
    """
    文章をベクトルDBに追加します。

    Args:
      diary_id (int): 日記ID。
      sentence (str): 追加する文章。

    Returns:
      None
    
    Raises:
      ValueError: コレクションがロードされていない場合。
    """
    if self.collection is None:
      raise ValueError("コレクションがロードされていません。load_collection() を先に呼び出してください。")
        
    self.collection.add(
      ids=[f"diary_id_{diary_id}"], # 主キーに相当する。UUIDを使用する予定。
      documents=[sentence],      
      metadatas=[{"user_id": self.user_id, "diary_id": diary_id}], # メタデータにユーザーIDと日記IDを追加
    )

  def find_by_sentence(self, sentence: str, top_k: int = 2) -> dict:
    """
    DB から sentence に類似するテキストを検索します。

    Args:
      sentence (str): 検索する文章。
        
    Returns:
      dict: 検索結果。{"documents": [類似する文章]} の形式。

    Raises:
      ValueError: DBがロードされていない場合。
    """
    if self.collection is None:
      raise ValueError("DBがロードされていません。load_collection関数を呼び出してください。")

    return self.collection.query(
      query_texts=[sentence],
      n_results=top_k
    )