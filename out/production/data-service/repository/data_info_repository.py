import logging
from bson import ObjectId

from app1.schemas.cursor import CursorPage

log = logging.getLogger()


class DataInfoRepository:

    def __init__(self, db, collection_name: str):
        self.collection = db[collection_name]

    async def find_cursor(
            self,
            limit: int = 10,
            cursor: str | None = None
    ) -> CursorPage:

        query = {}

        if cursor:
            query["_id"] = {"$gt": ObjectId(cursor)}

        cursor_db = (
            self.collection
            .find(query, {"_id": 0})
            .sort("_id", 1)
            .limit(limit + 1)
        )

        items = await cursor_db.to_list(length=limit + 1)

        has_next = len(items) > limit

        if has_next:
            items = items[:limit]

        next_cursor = None
        if items:
            last = await self.collection.find_one(
                {},
                sort=[("_id", 1)]
            )

            next_cursor = str(last["_id"]) if has_next else None

        if items:
            next_cursor = str(
                await self.collection
                .find(query)
                .sort("_id", 1)
                .skip(limit)
                .limit(1)
                .to_list(length=1)
            )

        return CursorPage(
            items=items,
            next_cursor=next_cursor,
            has_next=has_next
        )

