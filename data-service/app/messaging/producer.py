import json
import os

import aio_pika
from dotenv import load_dotenv

load_dotenv()


class RadiationProducer:
    def __init__(self, channel):
        self.exchange = channel.default_exchange

    async def publish(self, payload: list):
        message = aio_pika.Message(
            body=json.dumps({
                "type": "radiation.batch",
                "data": payload
            }).encode(),
            content_type="application/json"
        )

        await self.exchange.publish(message, routing_key=os.getenv("RABBIT_QUEUE_REC"))
