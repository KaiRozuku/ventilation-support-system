from core.mongo import mongo
from dao.data_info_dao import DataInfoDao


def get_data_dao():
    return DataInfoDao(mongo.db, "data_info")
