from fastapi import FastAPI, HTTPException
import httpx
import requests
import uvicorn
from scheduleplanner.applicationcore.applicationservice.application_service import ApplicationService
from scheduleplanner.web.loader.config_loader import ConfigLoader

PORT = 8000
HOST = "127.0.0.1"

class Controller:
  """
  FastAPIのコントローラークラスです。
  """

  def __init__(self):
    self.app = FastAPI()
    self.register_routes()

  def register_routes(self):
    """
    FastAPIのルートを登録します。
    """
    self.app.get("/user/{user_id}/{diary_id}")(self.get_diary_add_db)
    self.app.get("/analysis/scheduler/{user_id}")(self.get_schedule)

  def get_diary_add_db(self, user_id: int, diary_id: int) -> dict:
    """
    指定idのユーザーの指定idの日記を取得し、ベクトルDBに追加します。

    Args:
      user_id (int): ユーザーID。
      diary_id (int): 日記ID。

    Returns:
      dict: 日記が正常にDBに追加されたことを示すメッセージ。
    
    Raises:
      HTTPException: サーバーからのHTTPエラー応答が発生した場合。
      RequestException: 通信中にエラーが発生した場合。
      Exception: その他の予期しないエラー。
    """
    DIARY_API_URL = ConfigLoader().load_diary_get_url()
    url = f"{DIARY_API_URL}/{diary_id}"

    try:
      with httpx.Client() as client:
        response = client.get(url)
      response.raise_for_status()

    except httpx.HTTPStatusError as e:
      raise requests.RequestException(f"HTTPステータスエラー: {e.response.status_code} - {e.response.text}")
    except httpx.RequestError as e:
      raise requests.RequestException(f"ネットワークエラー: {e.__class__.__name__} - {str(e)}")
    except Exception as e:
      raise requests.RequestException(f"その他のエラーが発生しました: {str(e)}")

    response_json = response.json()

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

  async def get_schedule(self, user_id: int) -> str:
    """
    過去の日記を分析して、明日の予定を提案します。

    Args:
      user_id (int): ユーザーID。

    Returns:
      str: 明日の予定。
    """
    location = {
      "latitude": 35.7001076,
      "longitude": 139.9855455
    }

    try:
      application_service = ApplicationService(user_id)
      application_service.initialize_get_llm_output()
      response = application_service.get_llm_output(location)
      return response.choices[0].message.content

    except Exception as e:
      raise HTTPException(status_code=500, detail=f"予定立案に失敗しました: {str(e)}")


def main():
  """
  FastAPIアプリを起動します。
  """
  controller = Controller()
  app = controller.app
  uvicorn.run(app, host=HOST, port=PORT)
