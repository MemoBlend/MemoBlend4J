"""
データベースへ文章を追加することに失敗した場合の例外クラスです。
"""

from infrastructure.infrastructure_constants import InfrastructureConstants
from systemcommon.business_exception import BusinessException


class TextChunkSplitFailedException(BusinessException):
    """
    文章の分割に失敗した場合の例外クラスです。
    """

    def __init__(self):
        message = f"{InfrastructureConstants.CHUNK_OVERLAP} は {InfrastructureConstants.CHUNK_SIZE} 未満を指定してください"
        super().__init__(message)
