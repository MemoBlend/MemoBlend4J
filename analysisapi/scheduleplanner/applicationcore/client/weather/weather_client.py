import pandas as pd
import requests
import numpy as np
import json
import os

class WeatherClient:
  """
  外部APIを用いて、天気情報を取得するクラス。
  """

  def __init__(self):
    """
    WeatherClientのコンストラクタ。
    """
    pass

  def get_current_weather(self, latitude: float, longitude: float) -> str:
    """
    指定した緯度・経度に最も近い気象観測地点の現在の情報を取得する関数。

    Args:
      latitude (float): 緯度
      longitude (float): 経度
    
    Returns:
      str: 現在の天気情報（例："現在、雨が降っています。"）
    """
    try:
      # 気象庁の最新観測時刻を取得し、表示
      url = "https://www.jma.go.jp/bosai/amedas/data/latest_time.txt"
      print("天候取得時刻：", requests.get(url).text)

      # 気象庁の観測地点情報を取得
      station_url = "https://www.jma.go.jp/bosai/amedas/const/amedastable.json"
      response = requests.get(station_url)
      response.raise_for_status()
      data = response.json()
      df = pd.DataFrame(data).transpose()

      # 度分を十進法に変換
      df["lat"] = df["lat"].str[0] + df["lat"].str[1]/60
      df["lon"] = df["lon"].str[0] + df["lon"].str[1]/60

      # 対象地点との距離を算出し、最も近い地点を取得
      target_point = np.array([latitude, longitude])
      df['distance'] = df.apply(lambda row: self._calc_distance(row, target_point), axis=1)
      nearest  = df.loc[df["distance"].idxmin()]

      # 雨が降っているか否かを判定
      is_raining = nearest["elems"][0] == 1
      return "現在、雨が降っています。" if is_raining else "現在、雨は降っていません。"
    
    except Exception as e:
      return f"天気情報の取得に失敗しました: {e}"

  def get_tomorrow_weather(area_code: str) -> str:
    """
    作成中
    """
    url = f"https://www.jma.go.jp/bosai/forecast/data/forecast/{area_code}.json"
    try:
      response = requests.get(url)
      response.raise_for_status()
      data = response.json()
      # 明日の予報は [0]["timeSeries"][0]["areas"][0]["weathers"][1]
      weather_forecast = data[0]["timeSeries"][0]["areas"][0]["weathers"]
      tomorrow_weather = weather_forecast[1]  # index 1 が「明日」
      return f"明日の天気は「{tomorrow_weather}」です。"
    except Exception as e:
      return f"天気情報の取得に失敗しました: {e}"

  @staticmethod
  def _calc_distance(row: pd.Series, target_point: np.ndarray) -> float:
    """
    指定した地点と各地点の距離を計算する関数。

    Args:
      row (pd.Series): 各地点の緯度・経度情報
      target_point (np.ndarray): 検索する緯度・経度の配列
    
    Returns:
      float: 指定した地点と各地点の距離。
    """
    point = np.array([row['lat'], row['lon']])
    return np.linalg.norm(target_point - point)

  @staticmethod
  def _load_jma_codes(json_path: str = "jma_codes.json") -> dict:
    """
    作成中
    """
    try:
      with open(json_path, "r", encoding="utf-8") as f:
        return json.load(f)
    except Exception as e:
      raise RuntimeError(f"JMAコードの読み込みに失敗しました: {e}")

  def _get_jma_code_from_latlon(self, latitude: float, longitude: float) -> str:
    """
    作成中
    """
    json_path = os.path.join(os.path.dirname(__file__), "jma_codes.json")
    try:
      # 逆ジオコーディングAPI
      url = "https://nominatim.openstreetmap.org/reverse"
      params = {
        "lat": latitude,
        "lon": longitude,
        "format": "json",
        "accept-language": "ja"
      }
      headers = {"User-Agent": "WeatherFetcher/1.0"}
      response = requests.get(url, params=params, headers=headers)
      response.raise_for_status()

      # 都道府県を取得
      data = response.json()
      prefecture = data.get("address", {}).get("state")
      if not prefecture:
        return "都道府県が見つかりませんでした"

      # コード辞書をロード
      jma_codes = self._load_jma_codes(json_path)
      jma_code = jma_codes.get(prefecture)
      if not jma_code:
        return f"{prefecture} に対応するJMAコードが見つかりませんでした"
      
      return jma_code

    except Exception as e:
      return f"エラー: {e}"