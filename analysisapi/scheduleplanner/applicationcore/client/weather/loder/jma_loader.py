import json

class JMALoader:
  """
  地方気象台コード(json)を読み込むクラス。
  """
  def __init__(self, file_path: str="jma_codes.json"):
    """
    コンストラクタ。

    Args:
      file_path (str, optional): 設定ファイルのパス。デフォルトは "jma_codes.xml"。
    """
    self.file_path = file_path
  
  def load_jma_codes(self) -> dict:
    """
    JSONファイルから地方気象台コードを取得する。
    
    :return: 地方気象台コード。
    :rtype: dict
    """
    try:
      with open(self.json_path, "r", encoding="utf-8") as f:
        return json.load(f)
    except Exception as e:
      raise RuntimeError(f"JMAコードの読み込みに失敗しました: {e}")