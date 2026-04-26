from pydantic import BaseModel


class DataCreateRequest(BaseModel):
    full_weight_kg: float
    sample_weight_kg: float

    cs137_bq_kg: float
    sr90_bq_kg: float
    k40_bq_kg: float

    ra226_bq_kg: float
    th232_bq_kg: float

    cs137_kbq_m2: float
    sr90_kbq_m2: float

    ratio_cs_sr: float
    