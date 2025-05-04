class ApplicationserviceConstants:
    # OpenAI API 関連
    INPUT_FEE_PER_TOKEN_JPY = 0.0225 / 1000
    OUTPUT_FEE_PER_TOKEN_JPY = 0.0900 / 1000
    GPT_MODEL = "gpt-4o-mini-2024-07-18"

    # Function Calling 設定
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
