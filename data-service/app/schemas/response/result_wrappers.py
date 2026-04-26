from pydantic import BaseModel


class IdResponse(BaseModel):
    id: str


class UpdateResponse(BaseModel):
    updated: bool


class DeleteResponse(BaseModel):
    deleted_count: int
