from fastapi import APIRouter, Depends

from app.services.data_service import DataService
from app.dependencies.data_service import get_data_service
from schemas.response.data_response import DataInfoResponse

router = APIRouter(prefix="/data/info", tags=["data-info"])


@router.get("/", response_model=list[DataInfoResponse])
async def get_all(service: DataService = Depends(get_data_service)):
    return await service.get_all()


@router.get("/{item_id}", response_model=DataInfoResponse | None)
async def get_by_id(
        item_id: str,
        service: DataService = Depends(get_data_service)
):
    return await service.get_by_id(item_id)


@router.delete("/drop")
async def drop(service: DataService = Depends(get_data_service)):
    return {"deleted": await service.delete_all()}


@router.delete("/{item_id}")
async def drop_by_id(
        item_id: str,
        service: DataService = Depends(get_data_service)
):
    return {"deleted": await service.drop_by_id(item_id)}
