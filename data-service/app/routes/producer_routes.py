# routes/producer_routes.py

from fastapi import APIRouter, Depends

from app.services.rabbit_service import RabbitService
from app.dependencies.rabbit import get_rabbit_service

router = APIRouter(prefix="/producer")


@router.post("/send")
async def send_message(payload, rabbit: RabbitService = Depends(get_rabbit_service)):
    await rabbit.publish(queue_name="analytics_queue", payload=payload)
    return {"status": "sent"}
