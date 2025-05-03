import xml.etree.ElementTree as ET

class ConfigLoader:
  """
  設定ファイル（XML）を読み込むクラス。
  """

  def __init__(self, file_path: str="./scheduleplanner/web/property/config.xml"):
    """
    ConfigLoader クラスのコンストラクタ。

    Args:
      file_path (str, optional): 設定ファイルのパス。デフォルトは "config.xml"。
    """
    self.file_path = file_path
    self._root = None
    self._load_config()

  def load_diary_get_url(self) -> str:
    """
    日記APIのURLを取得する。

    Returns:
      str: 日記を1件取得するAPIのURL。
    """
    return self._get_url("diary_get_url")

  def load_diaries_get_url(self) -> str:
    """
    日記APIのURLを取得する関数。

    Returns:
      str: 日記のリストを取得するAPIのURL。
    """
    return self._get_url("diaries_get_url")
  
  def _load_config(self) -> None:
    """
    XML設定ファイルをパースしてルート要素を取得する関数。

    Raises:
      RuntimeError: XMLの読み込みに失敗した場合。
    """
    try:
      tree = ET.parse(self.file_path)
      self._root = tree.getroot()

    except Exception as e:
      raise RuntimeError(f"XMLの読み込みに失敗しました: {e}")

  def _get_url(self, tag_name: str) -> str:
    """
    指定されたタグ名に基づいてURLを取得する。

    Args:
      tag_name (str): 取得するURLのタグ名（例： "diary_get_url"）

    Returns:
      str: URL

    Raises:
      RuntimeError: 指定されたタグがXML内に存在しない場合
    """
    if self._root is None:
      raise RuntimeError("設定ファイルが読み込まれていません。")
    
    element = self._root.find(tag_name)
    if element is None:
      raise RuntimeError(f"{tag_name} が設定ファイルに存在しません。")
    
    return element.text