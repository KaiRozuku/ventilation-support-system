import asyncio
import logging

from messaging.producer import RadiationProducer


class BatchProducer:
    def __init__(
            self,
            generator,
            data_service,
            radiation_producer: RadiationProducer,
            batch_size: int = 100,
            interval: float = 30.0
    ):

        self.generator = generator
        self.data_service = data_service
        self.radiation_producer = radiation_producer
        self.batch_size = batch_size
        self.interval = interval

        self.running = False

        self.log = logging.getLogger(__name__)

    def update_config(
            self,
            batch_size: int | None = None,
            interval: float | None = None
    ):
        if batch_size is not None:
            self.batch_size = batch_size

        if interval is not None:
            self.interval = interval

    async def start(self):
        self.running = True
        while self.running:
            try:
                df = self.generator.generate_synthetic(self.batch_size)
                payload = df.to_dict(orient="records")

                await self.data_service.insert_many(payload)
                await self.radiation_producer.publish(payload)

                self.log.info("Published batch size=%s", len(payload))

                await asyncio.sleep(self.interval)

            except Exception as e:
                self.log.exception(e)

    def stop(self):
        self.running = False
