"""プロンプトの定型文章を定義するクラスです。"""


# pylint: disable=too-few-public-methods
class PromptConstants:
    """プロンプトの定型文章を定義するクラスです。"""

    USER_PROMPT_TEMPLATE = (
        "{diary_text} \n"
        "以上の文章は同一人物が書いた日記である。"
        "この人物は明日の休日の予定が決まっていない。"
        "現在位置の緯度は{latitude}、経度は{longitude}である。"
        "上記の日記から、この人物の明日の予定を決めて。\n"
        "# 条件\n"
        "- 複数の予定を時間列に沿って提案すること。\n"
        "- 日本語で出力すること。\n"
        "- **改行コード（\\n）や空白、インデント・空白・バックスラッシュ (\\) を一切含めない** “1 行の JSON” とする。\n"
        "- バッククォート、コードブロック、Markdown 記法は使用しない。\n"
        "- ルートは\"1\", \"2\", \"3\" ... の番号キーを持つ。\n"
        "- 各番号キーの値はオブジェクトで、必ず 4 つのキー 「point_name」「description」「start_time」「end_time」を含む。\n"
        "- 「point_name」は詳細な場所の名前、「description」はその場所の説明を含む。\n"
        "- 「start_time」は予定の開始時間、「end_time」は終了時間を含む。\n"
        "- 「start_time」「end_time」は ISO 8601 形式。\n"
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
