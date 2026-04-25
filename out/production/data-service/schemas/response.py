from typing import Any

from pydantic import BaseModel


class ResponseModel(BaseModel):
    def __init__(self, d, /, **data: Any):
        super().__init__(**data)
        self.__ans = d