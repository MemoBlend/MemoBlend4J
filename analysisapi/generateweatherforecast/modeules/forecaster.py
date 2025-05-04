import os
import time
import requests
import json
import xml.etree.ElementTree as ET
from tqdm import tqdm

API_URL = "https://weather.tsukumijima.net/api/forecast/city"
CITY_IDS_PATH = "./generateweatherforecast/property/city_ids.json"
FORECAST_OUTPUT_PATH = "./generateweatherforecast/property/weather_forecast.json"

class Forecast:
  def __init__(self):
    self.api_url = API_URL
    self.city_ids_path = CITY_IDS_PATH
    self.forecast_output_path = FORECAST_OUTPUT_PATH
    self.area_codes = self._load_or_fetch_area_codes()
  
  def get_tomorrow_forecast(self) -> dict:
    """ 
    明日の天気予報を取得して表示 
    
    取得する情報は以下の通り:
    - 地域名
    - 天気
    - 最低気温
    - 最高気温
    - 降水確率（時間帯ごと）
    
    Returns:
      dict: 明日の天気予報。
    """
    all_data = []

    for area_code in tqdm(self.area_codes, desc="全国の天気予報の取得中"):
      forecast_data = self._get_area_forecast(area_code)
      if forecast_data:
        location_name = forecast_data.get('location', {}).get('city', '不明な地域')
        forecasts = forecast_data.get('forecasts', [])

        for forecast in forecasts:
          if forecast.get('dateLabel') == '明日':
            weather = forecast.get('telop', '情報なし')
            temp_min = forecast.get('temperature', {}).get('min', {}).get('celsius', '情報なし')
            temp_max = forecast.get('temperature', {}).get('max', {}).get('celsius', '情報なし')

            # 降水確率（時間帯ごと）
            pop_data = forecast.get('chanceOfRain', {})
            pop_summary = {
              "00-06": pop_data.get("T00_06", "--"),
              "06-12": pop_data.get("T06_12", "--"),
              "12-18": pop_data.get("T12_18", "--"),
              "18-24": pop_data.get("T18_24", "--")
            }

            result = {
              "地域": location_name,
              "天気": weather,
              "最低気温（℃）": temp_min,
              "最高気温（℃）": temp_max,
              "降水確率": pop_summary
            }

            all_data.append(result)

    return all_data
  
  def _load_or_fetch_area_codes(self) -> list:
    """ 
    city_ids.json を読み込むか、なければRSSから取得して保存 
    
    Returns:
      list: 地域コードのリスト。
    """
    if not os.path.exists(self.city_ids_path):
      self._fetch_city_ids()

    with open(self.city_ids_path, "r", encoding="utf-8") as f:
      return json.load(f)

  def _fetch_city_ids(self) -> None:
    """ 
    RSSから city_id を取得して JSON に保存 
    参考：https://weather.tsukumijima.net/
    """
    rss_url = "https://weather.tsukumijima.net/primary_area.xml"
    response = requests.get(rss_url)
    if response.status_code != 200:
      raise Exception("RSSフィードの取得に失敗しました。")

    root = ET.fromstring(response.text)
    ids = [city.attrib.get('id') for city in root.iter('city') if city.attrib.get('id')]

    with open(self.city_ids_path, 'w', encoding='utf-8') as json_file:
      json.dump(ids, json_file, indent=4, ensure_ascii=False)
  
  def _get_area_forecast(self, area_code) -> dict:
    """ 
    指定した地域コードで天気予報を取得 
    
    Args:
      area_code (str): 地域コード。
    
    Returns:
      dict: 天気予報のデータ。
    """
    time.sleep(1)  # APIの呼び出し間隔を1秒に設定
    url = f"{self.api_url}/{area_code}"
    response = requests.get(url)
    if response.status_code == 200:
      return response.json()
    else:
      print(f"Failed to retrieve data for area code {area_code}")
      return None
