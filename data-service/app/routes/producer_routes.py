from fastapi import APIRouter, Request, Depends

from app.services.rabbit_service import RabbitService
from app.dependencies.rabbit import get_rabbit_service
from schemas.requests.producer_config import ProducerConfigRequest

router = APIRouter(prefix="/producer")


@router.patch("/config")
async def update_config(
        request: Request,
        body: ProducerConfigRequest
):
    producer = request.app.state.producer

    producer.update_config(
        batch_size=body.batch_size,
        interval=body.interval
    )

    return {
        "batch_size": producer.batch_size,
        "interval": producer.interval
    }


@router.post("/send")
async def send_message(payload, rabbit: RabbitService = Depends(get_rabbit_service)):
    await rabbit.publish(queue_name="analytics_queue", payload=payload)
    return {"status": "sent"}
