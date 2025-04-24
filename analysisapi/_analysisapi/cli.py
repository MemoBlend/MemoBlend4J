from fastapi import FastAPI, HTTPException
import httpx
import uvicorn
from analysisapi.ai_processor.diary_analyzer import DiaryAnalyzer
from analysisapi.loader.config_loader import ConfigLoader

# インスタンスの生成
app = FastAPI()
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

  # 日記分析AIのインスタンスを生成
  diary_analyzer = DiaryAnalyzer(response_json)

  # （未完成）DiaryAnalyzer クラスでAI解析を行う。
  location = {
    "latitude": 35.7001076,  # 船橋駅の緯度
    "longitude": 139.9855455  # 船橋駅の経度
  }
  response = diary_analyzer.analyze(location)

  return response.choices[0].message.content

def main():
  # uvicornでFastAPIアプリを起動
  uvicorn.run(app, host="127.0.0.1", port=8000)