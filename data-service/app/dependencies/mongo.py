from motor.motor_asyncio import AsyncIOMotorClient

from core.mongo import mongo


def get_db() -> AsyncIOMotorClient:
    if mongo.db is None:
        raise RuntimeError("MongoDB is not connected. Call connect() first.")
    return mongo.db
