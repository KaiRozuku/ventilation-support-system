from app.dependencies.data_generator import get_data_generator
from app.dependencies.data_loader import get_data_loader

from app.services.iot_service import IoTService


def get_iot_service():
    return IoTService(loader=get_data_loader(), generator=get_data_generator())
