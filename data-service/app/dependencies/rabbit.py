
import os
from app.services.rabbit_service import RabbitService


def get_rabbit_service():
    return RabbitService(os.getenv("RABBIT_URL"))
