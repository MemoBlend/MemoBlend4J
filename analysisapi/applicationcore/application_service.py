"""AnalysisAPIのアプリケーションサービスクラスです。"""

import json
from logging import INFO
from infrastructure.client.client_openai import ClientOpenAI
from infrastructure.client.client_weather import ClientWeather
from infrastructure.chromadb_repository import ChromadbRepository
from applicationcore.constants import Constants
from systemcommon.logger_config import LoggerConfig


class ApplicationService:
    """
    AnalysisAPIのアプリケーションサービスクラスです。
    """

    def __init__(self):
        self.db_repository = ChromadbRepository(persist=True)
        self.weather_client = ClientWeather()
        self.openai_client = ClientOpenAI()
        self.logger = LoggerConfig.get_logger(name=__name__, level=INFO)

    def add_text_to_vector_db(self, user_id: int, json_data: dict) -> None:
        """
        ベクトル DB に文章を追加します。
        Args:
            user_id (int): ユーザーID。
            json_data (dict): 追加するデータ。{"id": "1", "content": "sample text"}。

        Raises:
            ValueError: コレクションのロードまたは作成に失敗した場合。
        """
        self.logger.info("user_id: %sのデータをベクトルDBに追加します。", user_id)
        try:
            self.db_repository.add(
                user_id=user_id, diary_id=json_data["id"], sentence=json_data["content"]
            )
        except ValueError as e:
            self.logger.error(
                "user_id: %sのコレクションのロードまたは作成に失敗しました。", user_id
            )
            raise e

    def get_llm_output(self, location: dict, user_id: int) -> dict:
        """
        LLMに過去の日記を渡して、LLMの出力結果を受け取ります。
        以下の手順で実行されます。

        1. DB内の過去の日記から、必要な日記を複数件取得。
        2. OpenAI APIを用いて、明日の予定を提案する。外部APIを使用する場合は、Function Callingを使用。
        3. 提案された予定を返す。

        Args:
            location (dict): 現在位置の緯度・経度。{"latitude": 35.6895, "longitude": 139.6917} の形式。

        Returns:
            dict: LLMの出力結果。
        """
        self.logger.info("user_id: %s の過去の日記を取得します。", user_id)
        response = self.db_repository.find_by_sentence(
            user_id=user_id, sentence="明日の予定は？", n_results=2
        )
        diary_text = "\n".join(response["documents"][0])

        # ユーザー入力用プロンプトの作成
        user_prompt = Constants.USER_PROMPT_TEMPLATE.format(
            diary_text=diary_text,
            latitude=location["latitude"],
            longitude=location["longitude"],
        )

        # LLMに送信するメッセージを構築
        messages = [
            {"role": "system", "content": "あなたは、優秀なアドバイザーです"},
            {"role": "user", "content": user_prompt},
        ]

        # 最初のリクエスト（Function Calling が必要か否かの判断）
        response = self.openai_client.call_openai(
            messages,
            tools=Constants.FUNCTION_CALLING_DEFINITIONS,
            tool_choice="auto",
        )

        # Function Calling が必要な場合、ツールを実行
        tool_calls = response.choices[0].message.tool_calls
        if not tool_calls:
            return response

        for tool_call in tool_calls:
            if tool_call.function.name == "get_current_weather":
                args = json.loads(tool_call.function.arguments)

                weather = self.weather_client.get_current_weather(
                    latitude=args["latitude"], longitude=args["longitude"]
                )

                # プロンプトの更新
                messages.append(
                    {
                        "role": "function",
                        "name": "get_current_weather",
                        "content": weather,
                    }
                )

        # Function Calling の結果を反映し、再度 OpenAI API を呼び出す
        final_response = self.openai_client.call_openai(messages)

        return final_response
