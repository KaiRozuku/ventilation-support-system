from dependencies.data_repository import get_data_repository
from services.data_service import DataService


def get_data_service():
    return DataService(get_data_repository())
