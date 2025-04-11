import chromadb

class TextVectorizer:
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
      :param name: コレクションの名前
      """     
      self.collection = self.chroma_client.create_collection(name=self.user_id, get_or_create=True)

    def add_text(self, id: int, text: str):
      """
      テキストをコレクションに追加するメソッド。
      :param id: テキストのID
      :param text: 追加するテキスト
      """
      self.collection.add(
        documents=[text],
        ids=["id" + str(id)],
        metadatas=[{"source": self.user_id}],
      )

    def query_text(self, target_text: str):
      """
      コレクションから target_text に類似するテキストを検索するメソッド。
      事前にコレクションがロードされていない場合はエラーを返す。
      :param target_text: 検索するテキスト
      """
      if self.collection is None:
        raise ValueError("コレクションがロードされていません。load_collectionメソッドを呼び出してください。")
      results = self.collection.query(
        query_texts=[target_text],
        n_results=2
      )
      return results
  