from pydantic import BaseModel, Field
from typing import Optional


class RadionuclideEntity(BaseModel):
    id: Optional[str] = Field(alias="_id")

    full_weight_kg: float
    sample_weight_kg: float

    cs137_bq_kg: float
    sr90_bq_kg: float
    k40_bq_kg: float

    ra226_bq_kg: float
    th232_bq_kg: float
