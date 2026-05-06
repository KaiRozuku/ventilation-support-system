import os

import aio_pika
from dotenv import load_dotenv

load_dotenv()


class RabbitMQ:
    def __init__(self):
        self.connection = None
        self.channel = None
        self.queue = None

    async def connect(self):
        self.connection = await aio_pika.connect_robust(os.getenv("AMQP"))
        self.channel = await self.connection.channel()

        self.queue = await self.channel.declare_queue(
            os.getenv("RABBIT_QUEUE"),
            durable=True
        )

    async def close(self):
        if self.connection:
            await self.connection.close()


rabbitmq = RabbitMQ()
