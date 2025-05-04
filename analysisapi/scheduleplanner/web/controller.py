from fastapi import FastAPI, HTTPException
import httpx
import uvicorn
from scheduleplanner.applicationcore.applicationservice.application_service import ApplicationService
from scheduleplanner.web.loader.config_loader import ConfigLoader

# インスタンスの生成
app = FastAPI()
application_service = ApplicationService()


@app.get("/user/{user_id}/{diary_id}")
async def get_diary(user_id: int, diary_id: int) -> dict:
  """
  指定idのユーザーの指定idの日記を取得し、ベクトルDBに追加する関数。

  Args:
    user_id (int): ユーザーID
    diary_id (int): 日記ID
  
  Returns:
    dict: 処理結果メッセージ
  
  Raises:
    HTTPException: API呼び出しに失敗した場合
  """
  # xml情報からurlを組み立てる
  DIARY_API_URL = ConfigLoader().load_diary_get_url()
  url = f"{DIARY_API_URL}/{diary_id}"

  try:
    # 非同期処理でSpring BootのAPIを呼び出し
    async with httpx.AsyncClient() as client:
      response = await client.get(url)
    response.raise_for_status()  # 200番以外のレスポンスコードはエラー

  except httpx.HTTPStatusError as e:
    raise HTTPException(status_code=e.response.status_code, detail="Spring Boot APIエラー")
  except httpx.RequestError as e:
    raise HTTPException(status_code=500, detail=f"HTTPリクエストエラー: {str(e)}")

  # レスポンス結果をjson形式で取得
  response_json = response.json()

  # 日記分析AIのインスタンスを生成
  success, error = application_service.call_vectorizer(response_json, user_id)

  if not success:
    raise HTTPException(status_code=500, detail=f"ベクトルDBへの追加に失敗しました: {error}")

  return {"message": "日記が正常にDBに追加されました"}


@app.get("/analysis/scheduler/{user_id}")
async def get_schedule(user_id: int) -> str:
  """
  過去の日記を分析して、明日の予定を提案する関数。

  Args:
    user_id (int): ユーザーID
  
  Returns:
    str: 明日の予定
  
  Raises:
    HTTPException: 予定立案に失敗した場合
  """
  # 場所の指定
  location = {
    "latitude": 35.7001076,  # 船橋駅の緯度
    "longitude": 139.9855455  # 船橋駅の経度
  }

  try:
    # 予定の立案
    response = application_service.call_scheduler(user_id, location)
    return response.choices[0].message.content

  except Exception as e:
    raise HTTPException(status_code=500, detail=f"予定立案に失敗しました: {str(e)}")


def main():
  """
  FastAPIアプリを起動する関数。
  """
  uvicorn.run(app, host="127.0.0.1", port=8000)