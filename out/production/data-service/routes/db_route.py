from fastapi import APIRouter, Depends
from app1.ml.data_service import DataService
from app1.dependencies.data_service_dependency import get_data_service
from app1.configs.data_config import data_config
from app1.ml.analyser import NUMERIC_COLS

router = APIRouter()


@router.post("/validate")
async def validate(
        service: DataService = Depends(get_data_service)
):

    data = service.load_and_validate_data(
        data_config.file_name,
        NUMERIC_COLS
    )

    return {
        "rows": len(data)
    }


@router.post("/synthetic")
async def generate(
        service: DataService = Depends(get_data_service)
):

    data = service.generate_synthetic(
        NUMERIC_COLS,
        n_samples=3000
    )

    return {
        "data": data.head().to_dict()
    }