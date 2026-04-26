from pydantic import BaseModel, Field


class SyntheticRequest(BaseModel):
    n_samples: int = Field(
        default=3000,
        gt=0,
        le=100000
    )
