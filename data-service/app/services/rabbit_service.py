from aio_pika import Message, DeliveryMode, connect_robust


class RabbitService:
    def __init__(self, url: str):
        self.url = url
        self.connection = None
        self.channel = None

    async def connect(self):
        self.connection = await connect_robust(self.url)
        self.channel = await self.connection.channel()

    async def publish(self, queue_name: str, payload: bytes):
        await self.channel.default_exchange.publish(
            Message(body=payload, delivery_mode=DeliveryMode.PERSISTENT),
            routing_key=queue_name
        )
