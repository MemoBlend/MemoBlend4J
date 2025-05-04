from fastapi import FastAPI, HTTPException
import httpx
import uvicorn
from scheduleplanner.applicationcore.applicationservice.application_service import ApplicationService
from scheduleplanner.web.loader.config_loader import ConfigLoader

# インスタンスの生成
app = FastAPI()


@app.get("/user/{user_id}/{diary_id}")
async def get_diary(user_id: int, diary_id: int) -> dict:
  """
  指定idのユーザーの指定idの日記を取得し、ベクトルDBに追加します。

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
  try:
    application_service = ApplicationService(user_id)
    application_service.add_text_to_db(response_json)
    
  except (KeyError, TypeError, ValueError) as e:
    print(f"入力エラー: {e}")
  except ConnectionError as e:
    print(f"接続エラー: {e}")
  except Exception as e:
    print(f"その他の予期しないエラー: {e}")

  return {"message": "日記が正常にDBに追加されました"}


@app.get("/analysis/scheduler/{user_id}")
async def get_schedule(user_id: int) -> str:
  """
  過去の日記を分析して、明日の予定を提案します。

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
    application_service = ApplicationService(user_id)
    response = application_service.initialize_analizer(location)
    return response.choices[0].message.content

  except Exception as e:
    raise HTTPException(status_code=500, detail=f"予定立案に失敗しました: {str(e)}")


def main():
  """
  FastAPIアプリを起動します。
  """
  uvicorn.run(app, host="127.0.0.1", port=8000)