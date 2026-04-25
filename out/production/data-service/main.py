import logging

from fastapi import FastAPI
from contextlib import asynccontextmanager
from motor.motor_asyncio import AsyncIOMotorClient
import os


client = None
db = None
logger = logging.getLogger()


@asynccontextmanager
async def lifespan():
    global client, db

    client = AsyncIOMotorClient(os.getenv("MONGO_URI"))
    db = client["test_db"]

    logger.info("Mongo connected")

    yield

    client.close()
    logger.info("Mongo closed")


app = FastAPI(lifespan=lifespan)
