from schemas.response.data_response import DataInfoResponse


from entity.data_info import DataInfo


def to_entity(doc: dict) -> DataInfo:
    return DataInfo(
        oid=str(doc["_id"]),

        full_weight_kg=doc["Full_weight_kg"],
        sample_weight_kg=doc["Sample_weight_kg"],

        cs137_bq_kg=doc["137Cs_Bq_kg"],
        sr90_bq_kg=doc["90Sr_Bq_kg"],
        k40_bq_kg=doc["40K_Bq_kg"],

        ra226_bq_kg=doc["226Ra_Bq_kg"],
        th232_bq_kg=doc["232Th_Bq_kg"],

        cs137_kbq_m2=doc["137Cs_kBq_m2"],
        sr90_kbq_m2=doc["90Sr_kBq_m2"],

        ratio_cs_sr=doc["Ratio_Cs_Sr"],
    )


def to_document(e: DataInfo) -> dict:
    return {
        "Full_weight_kg": e.full_weight_kg,
        "Sample_weight_kg": e.sample_weight_kg,
        "137Cs_Bq_kg": e.cs137_bq_kg,
        "90Sr_Bq_kg": e.sr90_bq_kg,
        "40K_Bq_kg": e.k40_bq_kg,
        "226Ra_Bq_kg": e.ra226_bq_kg,
        "232Th_Bq_kg": e.th232_bq_kg,
        "137Cs_kBq_m2": e.cs137_kbq_m2,
        "90Sr_kBq_m2": e.sr90_kbq_m2,
        "Ratio_Cs_Sr": e.ratio_cs_sr,
    }


def to_dto(entity: DataInfo) -> DataInfoResponse:
    return DataInfoResponse(
        oid=entity.oid,

        full_weight_kg=entity.full_weight_kg,
        sample_weight_kg=entity.sample_weight_kg,

        cs137_bq_kg=entity.cs137_bq_kg,
        sr90_bq_kg=entity.sr90_bq_kg,
        k40_bq_kg=entity.k40_bq_kg,

        ra226_bq_kg=entity.ra226_bq_kg,
        th232_bq_kg=entity.th232_bq_kg,

        cs137_kbq_m2=entity.cs137_kbq_m2,
        sr90_kbq_m2=entity.sr90_kbq_m2,

        ratio_cs_sr=entity.ratio_cs_sr,
    )
