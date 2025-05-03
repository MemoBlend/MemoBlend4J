import json

class JMALoader:
  """
  地方気象台コード(json)を読み込むクラス。
  """

  def __init__(self, file_path: str="jma_codes.json"):
    """
    JMALoaderクラスのコンストラクタ。

    Args:
      file_path (str, optional): 設定ファイルのパス。デフォルトは "jma_codes.json"。
    """
    self.file_path = file_path
  
  def load_jma_codes(self) -> dict:
    """
    JSONファイルから地方気象台コードを取得する関数。

    Returns:
      dict: 地方気象台コード。
    
    Raises:
      FileNotFoundError: 指定されたファイルが見つからない場合。
      json.JSONDecodeError: JSONのデコードに失敗した場合。
      Exception: その他のエラーが発生した場合。
    """
    try:
      with open(self.file_path, "r", encoding="utf-8") as f:
        return json.load(f)

    except FileNotFoundError:
        raise RuntimeError(f"指定されたファイルが見つかりません: {self.file_path}")
    
    except json.JSONDecodeError:
        raise RuntimeError(f"指定されたファイルの読み込み中にエラーが発生しました: {self.file_path}")
    
    except Exception as e:
        raise RuntimeError(f"JMAコードの読み込みに失敗しました: {e}")