"""OpenAI APIを呼び出すクライアントクラスです。"""

from logging import INFO
import os
from openai import OpenAI
from applicationcore.constants import Constants
from systemcommon.logger_config import LoggerConfig


class ClientOpenAI:
    """
    OpenAI APIを呼び出すクライアントクラスです。
    """

    def __init__(self):
        """
        OpenAI APIを呼び出すクライアントクラスです。
        """
        self.open_api_client = OpenAI()
        self.open_api_client.api_key = os.getenv(key="OPENAI_API_KEY")
        self.logger = LoggerConfig.get_logger(name=__name__, level=INFO)
        self.total_prompt_tokens = 0
        self.total_completion_tokens = 0
        self.total_cost = 0.0

    def call_openai(self, messages: list[dict], tools=None, tool_choice=None) -> dict:
        """
        OpenAI APIを呼び出し、トークン使用量を計測します。

        Args:
            messages (list): メッセージリスト。
            tools (list | None): Function Calling用のツール定義。
            tool_choice (str | None): toolの選択方法。

        Returns:
            dict: APIレスポンス。
        """
        response = self.open_api_client.chat.completions.create(
            model=Constants.GPT_MODEL,
            messages=messages,
            tools=tools,
            tool_choice=tool_choice,
            max_tokens=1000,
        )

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
            self.total_prompt_tokens * Constants.INPUT_FEE_PER_TOKEN_JPY
            + self.total_completion_tokens * Constants.OUTPUT_FEE_PER_TOKEN_JPY
        )
        self.logger.info(
            "合計トークン数: %d",
            self.total_prompt_tokens + self.total_completion_tokens,
        )
        self.logger.info("合計料金: %s 円\n", self.total_cost)
