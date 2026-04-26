from app.dao.data_info_repository import DataInfoRepository
from dependencies.data_dao import get_data_dao


def get_data_repository():
    return DataInfoRepository(get_data_dao())
