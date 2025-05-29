"""天気に関する外部APIを呼び出すクライアントクラスです。"""

from logging import INFO
import pandas as pd
import requests
import numpy as np
from infrastructure.client.client_constants import ClientConstants
from systemcommon.logger_config import LoggerConfig


# pylint: disable=too-few-public-methods
class ClientWeather:
    """
    天気に関する外部APIを呼び出すクライアントクラスです。
    """

    def __init__(self):
        self.logger = LoggerConfig.get_logger(__name__, INFO)

    def get_current_weather(self, latitude: float, longitude: float) -> str:
        """
        指定した緯度・経度に最も近い気象観測地点の現在の天候を取得します。

        Args:
            latitude (float): 緯度
            longitude (float): 経度

        Returns:
            str: 現在の天候情報。雨が降っている場合は「現在、雨が降っています。」、降っていない場合は「現在、雨は降っていません。」。
        """
        response = requests.get(ClientConstants.AMEDAS_STATION_LOCATION_URL, timeout=10)
        response.raise_for_status()
        if self._is_raining_at_location(response.json(), latitude, longitude):
            return "現在、雨が降っています。"
        return "現在、雨は降っていません。"

    def _is_raining_at_location(
        self, data: dict, latitude: float, longitude: float
    ) -> bool:
        """
        観測データから対象地点に最も近い地点の天気を判定します。

        Returns:
            bool: 雨が降っている場合はTrue、降っていない場合はFalse。
        """
        df = pd.DataFrame(data).transpose()

        # 度分を十進法に変換
        df["lat"] = df["lat"].str[0] + df["lat"].str[1] / 60
        df["lon"] = df["lon"].str[0] + df["lon"].str[1] / 60

        # 対象地点との距離を算出し、最も近い地点を取得
        target_point = np.array([latitude, longitude])
        df["distance"] = df.apply(
            lambda row: self._calc_distance(row, target_point), axis=1
        )
        nearest = df.loc[df["distance"].idxmin()]
        # nearest["elems"][0] が 1 の場合、雨が降っていると判定します。
        return nearest["elems"][0] == 1

    def _calc_distance(self, row: pd.Series, target_point: np.ndarray) -> float:
        """
        指定した地点と各地点の距離を計算します。

        Args:
            row (pd.Series): 各地点の緯度・経度情報
            target_point (np.ndarray): 検索する緯度・経度の配列

        Returns:
            float: 指定した地点と各地点の距離。
        """
        point = np.array([row["lat"], row["lon"]])
        return np.linalg.norm(target_point - point)
