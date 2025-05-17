"""プロンプトの定型文章を定義するクラスです。"""


class PromptConstants:
    """プロンプトの定型文章を定義するクラスです。"""

    USER_PROMPT_TEMPLATE = (
        "{diary_text} 以上の文章は同一人物が書いた日記である。"
        "この人物は明日の休日の予定が決まっていない。"
        "現在位置の緯度は{latitude}、経度は{longitude}である。"
        "上記の日記から、この人物の明日の予定を決めて。"
        "ただし、以下の条件を守ること。\n"
        "1. 時間と細かい場所を指定すること。\n"
        "2. 簡潔に、マークダウン形式で表示すること。"
    )

    # Function Calling の設定
    FUNCTION_CALLING_DEFINITIONS = [
        {
            "type": "function",
            "function": {
                "name": "get_current_weather",
                "description": "指定した緯度経度の地点にて、現在雨が降っているか否かを返す。",
                "parameters": {
                    "type": "object",
                    "properties": {
                        "latitude": {"type": "number", "description": "緯度"},
                        "longitude": {"type": "number", "description": "経度"},
                    },
                    "required": ["latitude", "longitude"],
                },
            },
        }
    ]
