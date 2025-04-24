
from applicationcore.applicationservice.scheduler import Scheduler
from infrastructure.chromadb_repository import ChromadbRepository


class ApplicationService:
  def __init__(self):
    pass

  def call_vectorizer(self, json_data: dict = None, user_id: int = None):
    """
    ベクトルDBに文章を追加する。途中でエラーが発生した場合は False とエラーメッセージを返す。
    """
    try:
      self.json_data = json_data
      self.db_repository = ChromadbRepository(user_id, persist=True)
      self.db_repository.load_collection()
      self.db_repository.add(json_data['id'], json_data['content'])
      return True, None
    except Exception as e:
      return False, str(e)

  def call_scheduler(self, user_id: int=None, location: dict=None):
    self.Scheduler = Scheduler(user_id)
    return self.Scheduler.analyze(location)



