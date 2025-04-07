from fastapi import FastAPI, HTTPException
import httpx
import uvicorn
from analysisapi.ai_processor.diary_analyzer import DiaryAnalyzer
from analysisapi.loader.config_loader import ConfigLoader

# 定数
INPUT_TOKENS_FEE = 0.0225/1000  # inputでの1トークンあたりの料金(円)
OUTPUT_TOKENS_FEE = 0.0900/1000  # outputでの1トークンあたりの料金(円)

# インスタンスの生成
app = FastAPI()
diary_analyzer = DiaryAnalyzer()
config_loader = ConfigLoader()

@app.get("/diary/list/{user_id}")
async def get_diary(user_id: int):
  """
  Spring BootのAPIから指定idのユーザーの日記をすべて取得し、AI解析を行う。
  """

  # xmlから取得した日記apiへのurlを設定
  DIARY_API_URL = config_loader.load_diary_api_url()

  # urlを組み立てる
  url = f"{DIARY_API_URL}/{user_id}"

  # 非同期処理でSpring Bootのapiを呼び出し
  async with httpx.AsyncClient() as client:
    response = await client.get(url)
  
  # ステータスコードが200以外の場合はエラーを返す
  if response.status_code != 200:
    raise HTTPException(status_code=response.status_code, detail="Spring Boot APIエラー")

  response_json = response.json()

  # DiaryAnalyzer クラスでAI解析を行う
  response = diary_analyzer.analyze(response_json)

  # 解析に要した料金を表示
  print("合計使用トークン数：", response.usage.total_tokens)
  print("おおよその使用料金：", response.usage.prompt_tokens*INPUT_TOKENS_FEE + response.usage.completion_tokens*OUTPUT_TOKENS_FEE, "円")

  return response.choices[0].message.content

def main():
  # uvicornでFastAPIアプリを起動
  uvicorn.run(app, host="127.0.0.1", port=8000)
