import asyncio
from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.dependencies.iot_service import get_iot_service
from app.iot.batch_producere import BatchProducer
from core.eureka_init import register_eureka
from core.mongo import mongo
from dependencies.data_service import get_data_service


@asynccontextmanager
async def lifespan(_: FastAPI):
    mongo.connect()
    await register_eureka()
    iot_service = get_iot_service()
    data_service = get_data_service()

    producer = BatchProducer(
        generator=iot_service.generator,
        data_service=data_service,
        batch_size=100,
        interval=15.0
    )

    task = asyncio.create_task(producer.start())

    yield

    producer.stop()
    task.cancel()
    mongo.close()
