import asyncio
import logging

from app.services.data_service import DataService


class BatchProducer:

    def __init__(
            self,
            generator,
            data_service: DataService,
            batch_size: int = 100,
            interval: float = 30.0
    ):

        self.generator = generator
        self.data_service = data_service

        self.batch_size = batch_size
        self.interval = interval

        self.running = False

        self.log = logging.getLogger(__name__)

    async def start(self):
        self.running = True
        while self.running:
            try:
                df = self.generator.generate_synthetic(
                    self.batch_size
                )

                payload = df.to_dict(orient="records")

                await self.data_service.insert_many(payload)

                self.log.info(f"Inserted {len(payload)} rows")

                await asyncio.sleep(self.interval)

            except Exception as e:

                self.log.exception(e)

    def stop(self):
        self.running = False
