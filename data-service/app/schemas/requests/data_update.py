from pydantic import BaseModel


class DataUpdateRequest(BaseModel):
    full_weight_kg: float | None = None
    sample_weight_kg: float | None = None

    cs137_bq_kg: float | None = None
    sr90_bq_kg: float | None = None
    k40_bq_kg: float | None = None

    ra226_bq_kg: float | None = None
    th232_bq_kg: float | None = None

    cs137_kbq_m2: float | None = None
    sr90_kbq_m2: float | None = None

    ratio_cs_sr: float | None = None
