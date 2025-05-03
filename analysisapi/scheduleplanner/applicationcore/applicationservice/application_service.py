import json
import os
from openai import OpenAI
from applicationcore.client.weather.weather_client import WeatherClient
from infrastructure.chromadb_repository import ChromadbRepository

# 定数
INPUT_TOKENS_FEE = 0.0225/1000  # inputでの1トークンあたりの料金(円)
OUTPUT_TOKENS_FEE = 0.0900/1000  # outputでの1トークンあたりの料金(円)

class ApplicationService:
  def __init__(self):
    pass

  def call_vectorizer(self, json_data: dict = None, user_id: int = None):
    """
    ベクトルDBに文章を追加する。途中でエラーが発生した場合は False とエラーメッセージを返す。
    """
    try:
      self.json_data = json_data
      self.db_repository = ChromadbRepository(user_id=user_id, persist=True)
      self.db_repository.load_collection()
      self.db_repository.add(json_data['id'], json_data['content'])
      return True, None
    except Exception as e:
      return False, str(e)

  def call_scheduler(self, user_id: int=None, location: dict=None):
    self._initialize_scheduler(user_id)
    return self._analyze(location)
  
  def _initialize_scheduler(self, user_id: int=None):
    # ベクトルDBのコレクションを読み込み
    self.db_repository = ChromadbRepository(user_id=user_id, persist=True)
    self.db_repository.load_collection()

    # OpenAI APIの初期化
    self._initialize_openai_api()

  def _analyze(self, location: dict) -> dict:
    """
    （未完成）
    日記データをAIで解析するメソッド。
    
    :param location: 現在位置の緯度・経度
    :return: AI解析結果
    """
    response = self.db_repository.find_by_sentence("明日の予定は？")
    text = "\n".join(response['documents'][0])
    print("input text: ", text)
    messages = [
      {"role": "system", "content": "あなたは、優秀なアドバイザーです"},
      {"role": "user", "content": text +
        " 以上の文章は同一人物が書いた日記である。"
        "この人物は明日の休日の予定が決まっていない。"
        f"現在位置の緯度は{location['latitude']}、経度は{location['longitude']}である。"
        "上記の日記から、この人物の明日の予定を決めて。"
        "ただし、以下の条件を守ること。"
        "1. 時間と細かい場所を指定すること。"
        "2. 簡潔に、マークダウン形式で表示すること。"}
    ]
    # 最初のリクエスト（Function Calling が必要か否かの判断）
    response = self.client.chat.completions.create(
      model="gpt-4o-mini-2024-07-18",
      messages=messages,
      tools=self.function_calling,
      tool_choice="auto",
      max_tokens=1000
    )
    # 要したトークン数を取得
    self.total_prompt_tokens += response.usage.prompt_tokens
    self.total_completion_tokens += response.usage.completion_tokens
    # Function Calling の指示があれば処理する
    tool_calls = response.choices[0].message.tool_calls
    if tool_calls:
      for tool_call in tool_calls:
        function_name = tool_call.function.name
        arguments = json.loads(tool_call.function.arguments)
        # get_current_weather 関数が必要かを判断
        if function_name == "get_current_weather":
          result = self.weather_client.get_current_weather(
            latitude=arguments["latitude"],
            longitude=arguments["longitude"]
          )
          # 関数の結果をfunction roleで渡す
          messages.append({
            "role": "function",
            "name": function_name,
            "content": result
          })
      # 再度OpenAIへリクエストして最終出力を取得
      final_response = self.client.chat.completions.create(
        model="gpt-4o-mini-2024-07-18",
        messages=messages,
        max_tokens=1000
      )
      # 要したトークン数を取得
      self.total_prompt_tokens += final_response.usage.prompt_tokens
      self.total_completion_tokens += final_response.usage.completion_tokens
      # 合計料金を計算
      self.total_cost = (self.total_prompt_tokens * INPUT_TOKENS_FEE) + (self.total_completion_tokens * OUTPUT_TOKENS_FEE)
      print("合計トークン数:", self.total_prompt_tokens + self.total_completion_tokens, "トークン")
      print("合計料金:", self.total_cost, "円")
      return final_response
    # Function calling が使われなかった場合のレスポンス
    return response
  
  def _initialize_openai_api(self):
    """
    OpenAI APIの初期化を行う内部メソッド。
    """
    self.client = OpenAI()
    self.client.api_key = os.getenv("OPENAI_API_KEY")
    # function callingの設定
    self.weather_client = WeatherClient()
    self._setup_function_calling()
    # 金額表示用の変数
    self.total_prompt_tokens = 0
    self.total_completion_tokens = 0
    self.total_cost = 0.0

  def _setup_function_calling(self):
    """
    Function Callingの設定を行う内部メソッド。
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