import os
from openai import OpenAI
from fastapi import HTTPException

class DiaryAnalyzer:
  """
  日記データをAIで解析するクラス(作成中)。
  """

  def __init__(self):
    self.client = OpenAI()
    self.client.api_key = os.getenv("OPENAI_API_KEY")

  def analyze(self, json_data: dict):
    if "diaries" in json_data:
      text = "\n".join(diary["content"] for diary in json_data["diaries"])
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
    else:
      raise HTTPException(status_code=400, detail="日記データに 'diaries' キーが含まれていません")
