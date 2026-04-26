from dao.data_info_repository import DataInfoRepository
from mapper.mapper import to_dto
from entity.data_info import DataInfo


class DataService:
    def __init__(self, repo: DataInfoRepository):
        self.repo = repo

    async def get_all(self):
        return [to_dto(e) for e in await self.repo.get_all()]

    async def get_by_id(self, oid: str):
        entity = await self.repo.get_by_id(oid)
        return to_dto(entity) if entity else None

    async def drop_by_id(self, oid: str):
        return await self.repo.drop_by_id(oid)

    async def insert_many(self, data: list[dict]):
        entities = [DataInfo(oid=None, **item) for item in data]
        return await self.repo.insert_many(entities)

    async def delete_all(self):
        return await self.repo.delete_all()
