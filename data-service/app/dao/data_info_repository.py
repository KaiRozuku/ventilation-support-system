from dao.data_info_dao import DataInfoDao
from mapper.mapper import to_entity, to_document
from entity.data_info import DataInfo


class DataInfoRepository:

    def __init__(self, dao: DataInfoDao):
        self.dao = dao

    async def get_all(self) -> list[DataInfo]:
        docs = await self.dao.find_all()
        return [to_entity(d) for d in docs]

    async def get_by_id(self, oid: str) -> DataInfo | None:
        doc = await self.dao.find_by_id(oid)
        return to_entity(doc) if doc else None

    async def drop_by_id(self, oid: str) -> DataInfo | None:
        return await self.dao.drop_by_id(oid)

    async def delete_all(self) -> int:
        return await self.dao.delete_all()

    async def insert_many(self, entities: list[DataInfo]):
        docs = [to_document(e) for e in entities]
        result = await self.dao.insert_many(docs)
        return len(result.inserted_ids)
