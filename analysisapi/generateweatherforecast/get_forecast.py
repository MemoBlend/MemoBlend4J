import json
from generateweatherforecast.modeules.forecaster import Forecast

def save_forecast_to_json(data: list) -> None:
  """ 
  予報データをJSONファイルに保存する関数。
  予報データは、地域名、天気、最低気温、最高気温、降水確率を含む辞書のリストとして保存される。
  
  Args:
    data (list): 予報データのリスト
  """
  with open("./generateweatherforecast/property/weather_forecast.json", "w", encoding="utf-8") as f:
    json.dump(data, f, ensure_ascii=False, indent=2)

def main():
  forecast = Forecast()
  forecast_data = forecast.get_tomorrow_forecast()
  if forecast_data:
    save_forecast_to_json(forecast_data)