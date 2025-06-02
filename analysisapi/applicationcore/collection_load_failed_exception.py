"""
コレクションのロードに失敗した場合の例外クラスです。
"""

from systemcommon.business_exception import BusinessException


class CollectionLoadFailedException(BusinessException):
    """
    コレクションのロードに失敗した場合の例外クラスです。
    Attributes:
        user_id (int): ユーザーID 。
    """

    def __init__(self, user_id: int):
        message = f"user_id: {user_id}のコレクションのロードに失敗しました。"
        super().__init__(message)
