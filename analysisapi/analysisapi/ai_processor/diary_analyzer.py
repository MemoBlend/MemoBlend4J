import os
from openai import OpenAI
from fastapi import HTTPException
from analysisapi.ai_processor.text_vectorizer import TextVectorizer

class DiaryAnalyzer:
  """
  日記データをAIで解析するクラス(作成中)。
  """
  def __init__(self, json_data: dict = None):
    """
    コンストラクタ
    :param json_data: Spring Boot APIから取得した日記データ
    """
    self.client = OpenAI()
    self.client.api_key = os.getenv("OPENAI_API_KEY")
    self.json_data = json_data

    # 日記データの存在チェック
    if not self.json_data or "diaries" not in self.json_data:
      raise HTTPException(status_code=400, detail="日記データが無効です")

    # ベクトルDBに文章を追加
    self._add_text_to_db()

  def analyze(self) -> dict:
    """
    （未完成）
    Task1: query_textの内容の検討。
    Task2: LLMに投げる文章の数の検討。現状は類似度の上位2件を投げている。
    日記データをAIで解析するメソッド。
    :return: AI解析結果
    """
    query_result = self.vectorizer.query_text("明日の予定は？")
    print("query result: ", query_result)
    text = "\n".join(query_result['documents'][0])
    print("input text: ", text)
    response = self.client.chat.completions.create(
      model="gpt-4o-mini-2024-07-18",
      messages=[
        {"role": "system", "content": "あなたは、優秀なアドバイザーです"},
        {"role": "user", "content": text + 
            " 以上の文章は同一人物が書いた日記である。"
            "この人物は明日の休日の予定が決まっていない。"
            "この文章から、この人物の明日の予定を決めて。" 
            "ただし、以下の条件を守ること。"
            "1. 時間と細かい場所を指定すること"
            "2. 簡潔に、マークダウン形式で表示すること"},
            ],
      max_tokens=300
    )
    return response
    
  def _add_text_to_db(self):
    """
    ベクトルDBに日記の内容を追加する内部メソッド。
    """
    # 文章をベクトル化してベクトルDBに格納
    self.vectorizer = TextVectorizer(user_id=self.json_data["diaries"][0]["userId"], persist=False)
    self.vectorizer.load_collection()
    # （未完成）実際はベクトル化済みの内容は追加しないようにする。現在は、全て追加している。
    for diary in self.json_data["diaries"]:
      self.vectorizer.add_text(diary['id'], diary['content'])
