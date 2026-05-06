from fastapi import APIRouter, Depends
from fastapi import Request

from app.services.data_service import DataService
from app.dependencies.data_service import get_data_service
from schemas.response.data_response import DataInfoResponse

router = APIRouter(prefix="/data/measurements", tags=["measurements"])


@router.get("")
async def get_all(request: Request, service: DataService = Depends(get_data_service)):
    print("AUTH HEADER:", request.headers.get("authorization"))
    return await service.get_all()


@router.get("/{item_id}", response_model=DataInfoResponse | None)
async def get_by_id(item_id: str, service: DataService = Depends(get_data_service)):
    return await service.get_by_id(item_id)


@router.delete("")
async def delete_all(service: DataService = Depends(get_data_service)):
    return {"deleted": await service.delete_all()}


@router.delete("/{item_id}")
async def delete_by_id(item_id: str, service: DataService = Depends(get_data_service)):
    return {"deleted": await service.drop_by_id(item_id)}
