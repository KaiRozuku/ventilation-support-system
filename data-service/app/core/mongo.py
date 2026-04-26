# app/core/mongo.py
import os
from motor.motor_asyncio import AsyncIOMotorClient
from dotenv import load_dotenv

load_dotenv()


class MongoDB:
    def __init__(self):
        self.client: AsyncIOMotorClient | None = None
        self.db = None

    def connect(self):
        mongo_uri = os.getenv("MONGO_URI")
        db_name = os.getenv("MONGO_DB_NAME")

        if not mongo_uri:
            raise ValueError("MONGO_URI is not set in .env")

        self.client = AsyncIOMotorClient(mongo_uri)
        self.db = self.client[db_name]

    def close(self):
        if self.client:
            self.client.close()


mongo = MongoDB()
