from app1.entity.radionuclide_entity import RadionuclideEntity
from app1.schemas.radionuclide_dto import RadionuclideDto


def to_dto(entity: RadionuclideEntity) -> RadionuclideDto:
    return RadionuclideDto(
        cs137_bq_kg=entity.cs137_bq_kg,
        sr90_bq_kg=entity.sr90_bq_kg,
        risk_score=None
    )
