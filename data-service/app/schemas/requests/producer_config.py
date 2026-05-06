from pydantic import BaseModel


class ProducerConfigRequest(BaseModel):
    batch_size: int | None = None
    interval: float | None = None
