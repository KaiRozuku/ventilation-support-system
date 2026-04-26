from bson import ObjectId


class DataInfoDao:

    def __init__(self, db, collection_name: str):
        self.collection = db[collection_name]

    async def find_all(self):
        return await self.collection.find().to_list(length=None)

    async def find_by_id(self, oid: str):
        return await self.collection.find_one({
            "_id": ObjectId(oid)
        })

    async def drop_by_id(self, oid: str):
        res = await self.collection.delete_one(
            {"_id": ObjectId(oid)}
        )
        return res.deleted_count

    async def delete_all(self):
        res = await self.collection.delete_many({})
        return res.deleted_count

    async def delete_many(self):
        return await self.collection.drop()

    async def insert_many(self, docs: list[dict]):
        return await self.collection.insert_many(docs)
