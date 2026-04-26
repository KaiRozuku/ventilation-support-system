class DataInfo:
    def __init__(
            self,
            oid: str | None,
            full_weight_kg: float,
            sample_weight_kg: float,
            cs137_bq_kg: float,
            sr90_bq_kg: float,
            k40_bq_kg: float,
            ra226_bq_kg: float,
            th232_bq_kg: float,
            cs137_kbq_m2: float,
            sr90_kbq_m2: float,
            ratio_cs_sr: float,
    ):
        self.oid = oid
        self.full_weight_kg = full_weight_kg
        self.sample_weight_kg = sample_weight_kg
        self.cs137_bq_kg = cs137_bq_kg
        self.sr90_bq_kg = sr90_bq_kg
        self.k40_bq_kg = k40_bq_kg
        self.ra226_bq_kg = ra226_bq_kg
        self.th232_bq_kg = th232_bq_kg
        self.cs137_kbq_m2 = cs137_kbq_m2
        self.sr90_kbq_m2 = sr90_kbq_m2
        self.ratio_cs_sr = ratio_cs_sr
