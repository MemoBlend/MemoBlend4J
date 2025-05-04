import json
from typing import List, Dict
from generateweatherforecast.modeules.forecaster import Forecast

FORECAST_OUTPUT_PATH = "./generateweatherforecast/property/weather_forecast.json"

def save_forecast_to_json(data: List[Dict], output_path: str = FORECAST_OUTPUT_PATH) -> None:
    """
    予報データをJSONファイルに保存する関数。

    Args:
      data (List[Dict]): 地域ごとの天気予報のリスト
      output_path (str): 保存先のファイルパス
    """
    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)
        
def main() -> None:
  """
  天気予報を取得してJSONファイルに保存するメイン関数。
  """
  forecast = Forecast()
  forecast_data = forecast.get_tomorrow_forecast()
  if forecast_data:
    save_forecast_to_json(forecast_data)