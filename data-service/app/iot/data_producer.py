import asyncio


class DataProducer:
    def __init__(self, queue: asyncio.Queue, generator, interval: float = 1.0):
        self.queue = queue
        self.generator = generator
        self.interval = interval
        self.running = False

    async def start(self):
        self.running = True

        while self.running:
            data = self.generator()
            await self.queue.put(data)
            await asyncio.sleep(self.interval)

    def stop(self):
        self.running = False
