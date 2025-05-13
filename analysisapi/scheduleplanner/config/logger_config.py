from logging import getLogger, StreamHandler, INFO, DEBUG, WARNING, ERROR, CRITICAL

class LoggerConfig:
  """
  ログ設定を行うクラスです。
  ログレベルは、DEBUG、INFO、WARNING、ERROR、CRITICALのいずれかを指定できます。
  """
  
  @staticmethod
  def get_logger(name: str, level: int) -> getLogger:
    """
    指定された名前とログレベルでロガーを取得します。
    Args:
      name (str): ロガーの名前。
      level (int): ログレベル。DEBUG、INFO、WARNING、ERROR、CRITICALのいずれか。
    Returns:
      logger (getLogger): 設定されたロガー。
    Raises:
      ValueError: 指定されたログレベルが無効な場合。
    """
    if level not in [DEBUG, INFO, WARNING, ERROR, CRITICAL]:
      raise ValueError("ログレベルは、DEBUG、INFO、WARNING、ERROR、CRITICALのみ指定可能です。")

    logger = getLogger(name)
    if logger.hasHandlers():
      logger.handlers.clear()
    handler = StreamHandler()
    handler.setLevel(level)
    logger.setLevel(level)
    logger.addHandler(handler)
    logger.propagate = False

    return logger
