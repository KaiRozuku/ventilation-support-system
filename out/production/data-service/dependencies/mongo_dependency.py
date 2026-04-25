from motor.motor_asyncio import AsyncIOMotorClient

from app1.configs.mongo import MongoDB


def get_db() -> AsyncIOMotorClient:
    return MongoDB().db
