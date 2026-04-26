from pydantic import BaseModel


class SyntheticDataResponse(BaseModel):
    data: list[dict]
    count: int
