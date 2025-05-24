"""業務例外クラスです。"""


class BusinessException(Exception):
    """
    業務例外クラスです。
    このクラスは、業務ロジックに関連するエラーを表現します。
    """

    def __init__(self, message: str):
        """
        Args:
            message (str): エラーメッセージ。
        """
        super().__init__(message)
        self.message = message
