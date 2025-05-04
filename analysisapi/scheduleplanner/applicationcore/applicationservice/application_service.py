import json
import os
from openai import OpenAI
from scheduleplanner.applicationcore.client.weather.weather_client import WeatherClient
from scheduleplanner.infrastructure.chromadb_repository import ChromadbRepository

INPUT_FEE_PER_TOKEN_JPY = 0.0225/1000 
OUTPUT_FEE_PER_TOKEN_JPY = 0.0900/1000  
GPT_MODEL = "gpt-4o-mini-2024-07-18"  

class ApplicationService:
  """
  アプリケーションサービスクラスです。
  """
  def __init__(self, user_id: int=None) -> None:
    self.db_repo = ChromadbRepository(user_id=user_id, persist=True)
    self.db_repo.load_collection()

  def add_text_to_db(self, json_data: dict) -> None:
    """
    ベクトル DB に文章を追加します。

    Args:
      json_data (dict): 追加するデータ。{"id": "1", "content": "sample text"}。
    """
    self.db_repo.add(json_data['id'], json_data['content'])

  def initialize_analizer(self, location: dict=None) -> dict:
    """
    明日の予定を提案します。

    Args:
      location (dict): 現在位置の緯度・経度。{"latitude": 35.6895, "longitude": 139.6917} の形式。
    
    Returns:
      dict: OpenAI APIのレスポンス。
    """
    # 変数の初期化
    self.client = OpenAI()
    self.client.api_key = os.getenv("OPENAI_API_KEY")
    self.weather_client = WeatherClient()
    self._setup_function_calling()
    self.total_prompt_tokens = 0
    self.total_completion_tokens = 0
    self.total_cost = 0.0

    # リポジトリの初期化
    self.db_repository = self.db_repo

    return self._analyze(location)
  
  def _analyzer(self, location: dict) -> dict:
    """
    DBから過去の日記を取り出し、RAGを用いて明日の予定を提案します。
    以下の手順で実行されます。
    
    1. DB内の過去の日記から、必要な日記を複数件取得。
    2. OpenAI APIを用いて、明日の予定を提案する。外部APIを使用する場合は、Function Callingを使用。
    3. 提案された予定を返す。

    Args:
      location (dict): 現在位置の緯度・経度。{"latitude": 35.6895, "longitude": 139.6917} の形式。

    Returns:
      dict: AI解析結果。
    """
    response = self.db_repository.find_by_sentence("明日の予定は？")
    diary_text  = "\n".join(response['documents'][0])
    print("input text: ", diary_text )

    # ユーザープロンプトの作成
    user_prompt = (
      f"{diary_text } 以上の文章は同一人物が書いた日記である。"
      f"この人物は明日の休日の予定が決まっていない。"
      f"現在位置の緯度は{location['latitude']}、経度は{location['longitude']}である。"
      "上記の日記から、この人物の明日の予定を決めて。"
      "ただし、以下の条件を守ること。\n"
      "1. 時間と細かい場所を指定すること。\n"
      "2. 簡潔に、マークダウン形式で表示すること。"
    )

    # プロンプトの作成
    messages = [
      {"role": "system", "content": "あなたは、優秀なアドバイザーです"},
      {"role": "user", "content": user_prompt},
    ]

    # 最初のリクエスト（Function Calling が必要か否かの判断）
    response = self._call_openai(messages, tools=self.function_calling, tool_choice="auto")

    # Function Calling が必要な場合、ツールを実行
    tool_calls = response.choices[0].message.tool_calls
    if not tool_calls:
      return response
    
    for tool_call in tool_calls:
      if tool_call.function.name == "get_current_weather":
        args = json.loads(tool_call.function.arguments)
        
        # 天気予報の取得
        weather = self.weather_client.get_current_weather(
          latitude=args["latitude"],
          longitude=args["longitude"]
        )

        # プロンプトの更新
        messages.append({
          "role": "function",
          "name": "get_current_weather",
          "content": weather
        })

    # Function Calling の結果を反映し、再度 OpenAI API を呼び出す
    final_response = self._call_openai(messages)

    return final_response

  
  def _call_openai(self, messages: list[dict], tools=None, tool_choice=None) -> dict:
    """
    OpenAI APIを呼び出し、トークン使用量を計測します。

    Args:
      messages (list): メッセージリスト。
      tools (list | None): Function Calling用のツール定義。
      tool_choice (str | None): toolの選択方法。

    Returns:
      dict: APIレスポンス。
    """
    response = self.client.chat.completions.create(
      model=GPT_MODEL,
      messages=messages,
      tools=tools,
      tool_choice=tool_choice,
      max_tokens=1000
    )

    # トークン使用量の更新
    self._update_token_usage(response)
    
    return response

  def _update_token_usage(self, response: dict) -> None:
    """
    トークン数と料金を加算します。

    Args:
      response (dict): OpenAI APIのレスポンス。
    """
    self.total_prompt_tokens += response.usage.prompt_tokens
    self.total_completion_tokens += response.usage.completion_tokens
    self.total_cost = (
      self.total_prompt_tokens * INPUT_FEE_PER_TOKEN_JPY +
      self.total_completion_tokens * OUTPUT_FEE_PER_TOKEN_JPY
    )
    print("合計トークン数:", self.total_prompt_tokens + self.total_completion_tokens)
    print("合計料金:", self.total_cost, "円")

  def _setup_function_calling(self) -> None:
    """
    Function Callingの設定を行います。
    """
    self.function_calling=[
      {
        "type": "function",
        "function":{
          "name": "get_current_weather",
          "description": "指定した緯度経度の地点にて、現在雨が降っているか否かを返す。",
          "parameters":{
            "type": "object",
            "properties":{
              "latitude":{"type": "number", "description": "緯度"},
              "longitude":{"type": "number", "description": "経度"},
            },
            "required": ["latitude", "longitude"],
          },
        },
      }]