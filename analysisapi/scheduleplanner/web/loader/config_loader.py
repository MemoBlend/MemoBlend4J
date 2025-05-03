import xml.etree.ElementTree as ET

class ConfigLoader:
  """
  設定ファイル（XML）を読み込むクラス。
  """
  def __init__(self, file_path: str="./web/property/config.xml"):
    """
    ConfigLoader クラスのコンストラクタ。

    Args:
      file_path (str, optional): 設定ファイルのパス。デフォルトは "config.xml"。
    """
    self.file_path = file_path

  def load_diary_get_url(self):
    """
    XMLファイルから日記APIのURLを取得する。

    Returns:
      str: 日記を1件取得するAPIのURL。

    Raises:
      RuntimeError: XMLの読み込みに失敗した場合。
    """
    try:
      tree = ET.parse(self.file_path)
      root = tree.getroot()
      url = root.find("diary_get_url").text
      return url
    except Exception as e:
      raise RuntimeError(f"XMLの読み込みに失敗しました: {e}")

  def load_diaries_get_url(self):
    """
    XMLファイルから日記APIのURLを取得する。

    Returns:
      str: 日記のリストを取得するAPIのURL。

    Raises:
      RuntimeError: XMLの読み込みに失敗した場合。
    """
    try:
      tree = ET.parse(self.file_path)
      root = tree.getroot()
      url = root.find("diaries_get_url").text
      return url
    except Exception as e:
      raise RuntimeError(f"XMLの読み込みに失敗しました: {e}")
  