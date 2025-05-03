from fastapi import FastAPI, HTTPException
import httpx
import uvicorn
from scheduleplanner.applicationcore.applicationservice.application_service import ApplicationService
from scheduleplanner.web.loader.config_loader import ConfigLoader

# インスタンスの生成
app = FastAPI()
application_service = ApplicationService()

@app.get("/user/{user_id}/{diary_id}")
async def get_diary(user_id: int, diary_id: int):
  """
  指定idのユーザーの指定idの日記を取得し、ベクトルDBに追加する。
  """
  # xmlから取得した日記apiへのurlを設定
  config_loader = ConfigLoader()
  DIARY_API_URL = config_loader.load_diary_get_url()
  # urlを組み立てる（POST先のエンドポイント）
  url = f"{DIARY_API_URL}/{diary_id}"

  # 非同期処理でSpring Bootのapiを呼び出し
  async with httpx.AsyncClient() as client:
    response = await client.get(url)

  if response.status_code != 200:
    raise HTTPException(status_code=response.status_code, detail="Spring Boot APIエラー")

  # レスポンス結果をjson形式で取得
  response_json = response.json()

  # 日記分析AIのインスタンスを生成
  success, error = application_service.call_vectorizer(response_json, user_id)

  if not success:
    raise HTTPException(status_code=500, detail="ベクトルDBへの追加に失敗しました：" + error)

  return {"message": "日記が正常にDBに追加されました"}


@app.get("/analysis/scheduler/{user_id}")
async def get_schedule(user_id: int):
  """
  過去の日記を分析して、明日の予定を提案する。
  """
  # 場所の指定
  location = {
    "latitude": 35.7001076,  # 船橋駅の緯度
    "longitude": 139.9855455  # 船橋駅の経度
  }
  # 予定の立案
  response = application_service.call_scheduler(user_id, location)

  return response.choices[0].message.content


def main():
  # uvicornでFastAPIアプリを起動
  uvicorn.run(app, host="127.0.0.1", port=8000)