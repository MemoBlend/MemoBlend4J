import pandas as pd
import requests
import numpy as np

TIME_URL = "https://www.jma.go.jp/bosai/amedas/data/latest_time.txt"
STATION_URL = "https://www.jma.go.jp/bosai/amedas/const/amedastable.json"

class WeatherClient:
  """
  外部APIを用いて、天気情報を取得するクラスです。
  """

  def __init__(self):
    pass

  def get_current_weather(self, latitude: float, longitude: float) -> str:
    """
    指定した緯度・経度に最も近い気象観測地点の現在の天候を取得します。

    Args:
      latitude (float): 緯度
      longitude (float): 経度
    
    Returns:
      str: 現在の天気情報（例："現在、雨が降っています。"）
    
    Raises:
      RequestException: HTTPリクエストに失敗した場合
      Exception: その他の予期しないエラー
    """
    try:
      # 気象庁APIに接続して観測地点情報を取得
      data = self._fetch_weather_station_data()
      return self._analyze_weather_data(data, latitude, longitude)
    
    except requests.RequestException as e:
      raise requests.RequestException(f"HTTPリクエストに失敗しました: {e}")  
    except Exception as e:
      raise requests.RequestException(f"その他のエラーが発生しました: {e}")
  

  def _fetch_weather_station_data(self) -> dict:
    """
    気象庁APIから観測地点情報を取得します。
    
    Returns:
      dict: JSON形式の観測地点データ
    """
    # 最新観測時刻の取得
    print("天候取得時刻：", requests.get(TIME_URL).text)

    # 観測地点データの取得
    response = requests.get(STATION_URL)
    response.raise_for_status()

    return response.json()


  def _analyze_weather_data(self, data: dict, latitude: float, longitude: float) -> str:
    """
    観測データから対象地点に最も近い地点の天気を判定します。
    
    Returns:
      str: 天候情報（例："現在、雨が降っています。"）
    """
    df = pd.DataFrame(data).transpose()

    # 度分を十進法に変換
    df["lat"] = df["lat"].str[0] + df["lat"].str[1] / 60
    df["lon"] = df["lon"].str[0] + df["lon"].str[1] / 60

    # 対象地点との距離を算出し、最も近い地点を取得
    target_point = np.array([latitude, longitude])
    df['distance'] = df.apply(lambda row: self._calc_distance(row, target_point), axis=1)
    nearest = df.loc[df["distance"].idxmin()]

    # 雨の判定
    is_raining = nearest["elems"][0] == 1
    return "現在、雨が降っています。" if is_raining else "現在、雨は降っていません。"


  def _calc_distance(self, row: pd.Series, target_point: np.ndarray) -> float:
    """
    指定した地点と各地点の距離を計算します。

    Args:
      row (pd.Series): 各地点の緯度・経度情報
      target_point (np.ndarray): 検索する緯度・経度の配列
    
    Returns:
      float: 指定した地点と各地点の距離。
    """
    point = np.array([row['lat'], row['lon']])
    return np.linalg.norm(target_point - point)