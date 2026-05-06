import asyncio
from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.dependencies.iot_service import get_iot_service
from core.eureka_init import register_eureka
from core.mongo import mongo
from core.rabbit import rabbitmq
from dependencies.data_service import get_data_service
from iot.batch_producer import BatchProducer
from messaging.producer import RadiationProducer


@asynccontextmanager
async def lifespan(app: FastAPI):
    await mongo.connect()
    await register_eureka()
    await rabbitmq.connect()

    radiation_producer = RadiationProducer(
        rabbitmq.channel
    )

    iot_service = get_iot_service()
    data_service = get_data_service()

    producer = BatchProducer(
        generator=iot_service.generator,
        data_service=data_service,
        radiation_producer=radiation_producer,
        batch_size=1000,
        interval=10000.0
    )

    app.state.producer = producer

    task = asyncio.create_task(producer.start())

    try:
        yield

    finally:
        producer.stop()
        task.cancel()
        try:
            await task
        except asyncio.CancelledError:
            pass

        await rabbitmq.close()
        await mongo.close()
