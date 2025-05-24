"""システム例外クラスです。"""


class SystemException(Exception):
    """
    システム例外クラスです。
    このクラスは、システムに関連するエラーを表現します。
    """

    def __init__(self, message: str):
        """
        Args:
            message (str): エラーメッセージ。
        """
        super().__init__(message)
        self.message = message
        self.error_code = 500
        self.error_message = message
        self.error_type = "SystemException"
        self.error_detail = "システムエラーが発生しました。"
