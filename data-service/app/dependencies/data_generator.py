from app.dependencies.data_loader import get_data_loader
from app.iot.data_generator import DataGenerator


def get_data_generator() -> DataGenerator:
    return DataGenerator(get_data_loader().load_and_validate_data())
