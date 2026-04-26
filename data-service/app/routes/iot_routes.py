from fastapi import APIRouter, Depends

from app.dependencies.iot_service import get_iot_service
from app.schemas.requests.synthetic_request import SyntheticRequest
from app.schemas.response.response import SyntheticDataResponse
from app.services.iot_service import IoTService

router = APIRouter(prefix="/iot", tags=["IoT"])


@router.post("/validate")
async def validate(service: IoTService = Depends(get_iot_service)):
    return service.validate_data()


@router.post("/synthetic", response_model=SyntheticDataResponse)
async def generate(request: SyntheticRequest, service: IoTService = Depends(get_iot_service)):
    data = service.generate_synthetic(request.n_samples)
    return SyntheticDataResponse(count=len(data), data=data[:5])
