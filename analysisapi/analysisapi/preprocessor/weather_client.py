import pandas as pd
import requests
import numpy as np

def get_current_weather(latitude: float, longitude: float) -> pd.DataFrame:
  """
  指定した緯度・経度に最も近い気象観測地点の現在の情報を取得する関数。
  :param latitude: 緯度
  :param longitude: 経度
  :return: 最も近い地点の情報を含むDataFrame
  """
  # 気象庁の最新観測時刻を取得
  url = "https://www.jma.go.jp/bosai/amedas/data/latest_time.txt"
  print("取得時刻：", requests.get(url).text)
  # 気象庁の観測地点情報を取得
  url = "https://www.jma.go.jp/bosai/amedas/const/amedastable.json"
  with requests.get(url) as response:
    json = response.json()
    df = pd.DataFrame(json).transpose()
  # 取得した緯度latと経度lonは度分秒のため，十進度に変換
  df["lat"] = df["lat"].str[0] + df["lat"].str[1]/60
  df["lon"] = df["lon"].str[0] + df["lon"].str[1]/60
  # 検索する緯度経度の指定
  target_point = np.array([latitude, longitude])
  # target_pointとの距離を DataFrame に追加
  df['distance'] = df.apply(lambda row: _calc_distance(row, target_point), axis=1)
  # 最も近い地点のデータを返す
  return df.loc[df["distance"].idxmin()]

def _calc_distance(row: pd.Series, target_point: np):
  """
  指定した地点と各地点の距離を計算する関数。
  :param row: 各地点のデータ
  :param target_point: 指定した地点の緯度・経度
  :return: 距離
  """
  point = np.array([row['lat'], row['lon']])
  return np.linalg.norm(target_point - point)

print(get_current_weather(34.7108, 137.7261)) # 34.7108, 137.7261は静岡県浜松市の緯度経度