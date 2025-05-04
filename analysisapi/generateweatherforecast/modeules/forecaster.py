import os
import time
import json
import requests
import xml.etree.ElementTree as ET
from tqdm import tqdm
from typing import List, Dict, Optional

API_URL = "https://weather.tsukumijima.net/api/forecast/city"
RSS_URL = "https://weather.tsukumijima.net/primary_area.xml"
CITY_IDS_PATH = "./generateweatherforecast/property/city_ids.json"

class Forecast:
  def __init__(self, request_delay: float=1.0):
    """
    Forecastクラスのコンストラクタです。
    
    Args:
      request_delay (float): APIリクエスト間隔（秒）
    """
    self.area_codes = self._load_or_fetch_area_codes()
    self.request_delay = request_delay

  def get_tomorrow_forecast(self) -> List[Dict]:
    """
    全国の明日の天気予報を取得

    Returns:
      List[Dict]: 地域ごとの天気予報リスト
    """
    all_data = []

    for area_code in tqdm(self.area_codes, desc="全国の天気予報を取得中..."):
      forecast = self._fetch_forecast_for_area(area_code)
      if forecast:
        data = self._parse_tomorrow_forecast(forecast)
        if data:
          all_data.append(data)

    return all_data

  def _load_or_fetch_area_codes(self) -> List[str]:
      """
      地域コード一覧を読み込み、なければ取得して保存。

      Returns:
        List[str]: 地域コードリスト
      """
      if not os.path.exists(CITY_IDS_PATH):
        self._fetch_and_save_city_ids()

      with open(CITY_IDS_PATH, "r", encoding="utf-8") as f:
        return json.load(f)

  def _fetch_and_save_city_ids(self) -> None:
    """
    RSSから地域コードを取得してJSONに保存。
    """
    try:
      response = requests.get(RSS_URL)
      response.raise_for_status()
    except requests.RequestException as e:
      raise RuntimeError(f"RSSフィードの取得に失敗しました: {e}")

    root = ET.fromstring(response.text)
    city_ids = [city.attrib["id"] for city in root.iter("city") if "id" in city.attrib]

    os.makedirs(os.path.dirname(CITY_IDS_PATH), exist_ok=True)
    with open(CITY_IDS_PATH, "w", encoding="utf-8") as f:
      json.dump(city_ids, f, ensure_ascii=False, indent=2)

  def _fetch_forecast_for_area(self, area_code: str) -> Optional[Dict]:
    """
    指定地域の天気予報を取得。

    Args:
      area_code (str): 地域コード

    Returns:
      Optional[Dict]: APIからのレスポンス
    """
    time.sleep(self.request_delay)
    try:
      response = requests.get(f"{API_URL}/{area_code}")
      response.raise_for_status()
      return response.json()
    except requests.RequestException:
      return None

  def _parse_tomorrow_forecast(self, forecast_data: Dict) -> Optional[Dict]:
      """
      APIレスポンスから「明日」のデータを抽出。

      Args:
        forecast_data (Dict): APIレスポンス

      Returns:
        Optional[Dict]: 明日の天気情報
      """
      location = forecast_data.get("location", {}).get("city", "不明な地域")
      forecasts = forecast_data.get("forecasts", [])

      for forecast in forecasts:
        if forecast.get("dateLabel") == "明日":
          return {
            "地域": location,
            "天気": forecast.get("telop", "情報なし"),
            "最低気温（℃）": forecast.get("temperature", {}).get("min", {}).get("celsius", "情報なし"),
            "最高気温（℃）": forecast.get("temperature", {}).get("max", {}).get("celsius", "情報なし"),
            "降水確率": {
              "00-06": forecast.get("chanceOfRain", {}).get("T00_06", "--"),
              "06-12": forecast.get("chanceOfRain", {}).get("T06_12", "--"),
              "12-18": forecast.get("chanceOfRain", {}).get("T12_18", "--"),
              "18-24": forecast.get("chanceOfRain", {}).get("T18_24", "--"),
            },
          }
      return None