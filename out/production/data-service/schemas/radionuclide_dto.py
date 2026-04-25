from pydantic import BaseModel


class RadionuclideDto(BaseModel):

    cs137_bq_kg: float
    sr90_bq_kg: float

    risk_score: float | None = None
