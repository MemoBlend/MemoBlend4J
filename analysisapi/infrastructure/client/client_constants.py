"""クライアントの定数を定義するクラスです。"""


class ClientConstants:
    """
    クライアントの定数を定義するクラスです。
    """

    INPUT_FEE_PER_TOKEN_JPY = 0.0225 / 1000
    OUTPUT_FEE_PER_TOKEN_JPY = 0.0900 / 1000
    GPT_MODEL = "gpt-4o-mini-2024-07-18"

    WEATHER_DATA_FETCH_TIME_URL = (
        "https://www.jma.go.jp/bosai/amedas/data/latest_time.txt"
    )
    AMEDAS_STATION_LOCATION_URL = (
        "https://www.jma.go.jp/bosai/amedas/const/amedastable.json"
    )
