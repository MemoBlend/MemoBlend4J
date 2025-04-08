import chromadb

class TextVectorizer:
    def __init__(self, persist_directory: str = "./chroma_db"):
      # DBを保存する場合
      # chroma_client = chromadb.PersistentClient(path=persist_directory)

      # DBを保存しない場合
      self.chroma_client = chromadb.Client()

      self.collection = None

    def load_collection(self, name: str = "memo_blend"):        
      # コレクションを作成または取得
      self.collection = self.chroma_client.create_collection(name=name, get_or_create=True)

    def add_text(self, text: str):
      # ドキュメントを追加
      self.collection.add(
        documents=[
          "今日はハワイに行く予定です",
          "これはオレンジに関するドキュメントです",
          "明日は晴れるといいな",
        ],
        ids=["id1", "id2", "id3"],
        metadatas=[
          {"source": "memo1"},
          {"source": "memo2"},
          {"source": "memo3"},
        ],
      )

    def serch_text(self, text: str):
      results = self.collection.query(
        query_texts=["楽しい日記を探したい"],
        n_results=2
      )
      return results
  