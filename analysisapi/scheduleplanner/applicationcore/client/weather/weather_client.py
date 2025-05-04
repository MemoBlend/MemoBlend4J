import pandas as pd
import requests
import numpy as np
import json
import os

class WeatherClient:
  """
  外部APIを用いて、天気情報を取得するクラスです。
  """

  def __init__(self):
    """
    WeatherClientクラスのコンストラクタです。
    """
    pass

  def get_current_weather(self, latitude: float, longitude: float) -> str:
    """
    指定した緯度・経度に最も近い気象観測地点の現在の情報を取得します。

    Args:
      latitude (float): 緯度
      longitude (float): 経度
    
    Returns:
      str: 現在の天気情報（例："現在、雨が降っています。"）
    
    Raises:
      HTTPError: HTTPリクエストに失敗した場合
      ConnectionError: 接続に失敗した場合
      TimeoutError: タイムアウトが発生した場合
      RequestException: その他のリクエストエラー
      KeyError: データの処理中にキーが見つからない場合
      TypeError: データの型が不正な場合
      ValueError: データの値が不正な場合
      Exception: その他の予期しないエラー
    """
    # 気象庁APIに接続
    try:
      # 気象庁の最新観測時刻を取得し、表示
      url = "https://www.jma.go.jp/bosai/amedas/data/latest_time.txt"
      print("天候取得時刻：", requests.get(url).text)

      # 気象庁の観測地点情報を取得
      station_url = "https://www.jma.go.jp/bosai/amedas/const/amedastable.json"
      response = requests.get(station_url)
      response.raise_for_status()
      data = response.json()

    except requests.exceptions.HTTPError as e:
      return f"HTTPエラーが発生しました: {e}"
    except requests.exceptions.ConnectionError as e:
      return f"接続エラーが発生しました: {e}"
    except requests.exceptions.Timeout as e:
      return f"タイムアウトエラーが発生しました: {e}"
    except requests.exceptions.RequestException as e:
      return f"リクエストエラーが発生しました: {e}"
    except Exception as e:
      return f"気象庁API接続にて、その他のエラーが発生しました: {e}"

    # 観測地点情報をDataFrameに変換し、計算
    try:
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
    
    except KeyError as e:
      return f"データのキーが見つかりません: {e}"
    except TypeError as e:
      return f"型のエラーが発生しました: {e}"
    except ValueError as e:
      return f"値のエラーが発生しました: {e}"
    except Exception as e:
      return f"データ処理にて、その他のエラーが発生しました: {e}"

  @staticmethod
  def _calc_distance(row: pd.Series, target_point: np.ndarray) -> float:
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