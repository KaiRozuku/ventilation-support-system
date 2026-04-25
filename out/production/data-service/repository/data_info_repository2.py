# import asyncio
# import logging
# from pathlib import Path
#
# import pandas as pd
# from dotenv import load_dotenv
# from motor.motor_asyncio import (
#     AsyncIOMotorClient,
#     AsyncIOMotorDatabase,
#     AsyncIOMotorCollection
# )
#
# import os
#
# load_dotenv()
#
# BATCH_SIZE = 100
# DELAY_SECONDS = 2
#
#
# class DataInfoRepository:
#
#     def __init__(self, db: AsyncIOMotorDatabase, collection_name: str):
#         self.db = db
#         self.collection: AsyncIOMotorCollection = (
#             db[collection_name]
#         )
#         self.log = logging.getLogger()
#
#     async def insert_csv_batches(self, csv_path: Path):
#         df = pd.read_csv(csv_path)
#         df = df.where(pd.notnull(df), None)
#         documents = df.to_dict(orient="records")
#         total = len(documents)
#
#         for i in range(0, total, BATCH_SIZE):
#             batch = documents[i:i + BATCH_SIZE]
#             result = await self.collection.insert_many(batch)
#
#             self.log.info(
#                 "Inserted %s docs into %s (%s/%s)",
#                 len(result.inserted_ids),
#                 self.collection.name,
#                 i + len(batch),
#                 total
#             )
#
#             await asyncio.sleep(DELAY_SECONDS)
#
#         self.log.info("DONE")
#
#
# # async def main():
# #
# #     client = AsyncIOMotorClient(
# #         os.getenv("MONGO_URI")
# #     )
# #
# #     try:
# #
# #         db_name = os.getenv("MONGO_DB")
# #         db = client[db_name]
# #
# #         repo = DataInfoRepository(
# #             db=db,
# #             collection_name="synthetic_data"
# #         )
# #
# #         BASE_DIR = Path(__file__).resolve()
# #         PROJECT_ROOT = BASE_DIR.parents[2]
# #
# #         csv_path = (
# #                 PROJECT_ROOT
# #                 / "resource"
# #                 / "synthetic_radionuclide_data52.csv"
# #         )
# #         logging.info("GOOD!!!")
# #         await repo.insert_csv_batches(csv_path)
# #
# #     finally:
# #         client.close()
# #
# #
# # asyncio.run(main())
#
#
# async def main():
#
#     client = AsyncIOMotorClient(
#         os.getenv("MONGO_URI")
#     )
#
#     db = client[os.getenv("MONGO_DB")]
#
#     collection = db["synthetic_data"]
#
#     cursor = collection.find().limit(5)
#
#     async for doc in cursor:
#         print(doc)
#
#
# asyncio.run(main())
