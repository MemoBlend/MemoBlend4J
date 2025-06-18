"""日記のベクトルDBに登録するためのリクエストDTOです。"""

from pydantic import BaseModel


class PostDiaryRequest(BaseModel):
    """日記のベクトルDBに登録するためのリクエストDTOです。"""

    id: int
    diary_text: str
