"""天気予報を取得するバッチアプリケーションのメインモジュールです。"""

import os
import json
from typing import List, Dict
from datetime import datetime, timedelta
from fetchforecastbatch.forecast_client import ForecastClient


# 明日の日付を取得し、ファイル名に指定
tomorrow = datetime.now() + timedelta(days=1)
tomorrow_str = tomorrow.strftime("%Y_%m_%d")
FORECAST_OUTPUT_PATH = f"./fetchforecastbatch/property/output/{tomorrow_str}.json"


def save_forecast_to_json(
    data: List[Dict], output_path: str = FORECAST_OUTPUT_PATH
) -> None:
    """
    予報データをJSONファイルに保存します。

    Args:
      data (List[Dict]): 地域ごとの天気予報のリスト
      output_path (str): 保存先のファイルパス
    """
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)


def main() -> None:
    """
    天気予報を取得してJSONファイルに保存します。
    """
    forecast_client = ForecastClient()
    forecast_data = forecast_client.get_tomorrow_forecast()
    if forecast_data:
        save_forecast_to_json(forecast_data)
