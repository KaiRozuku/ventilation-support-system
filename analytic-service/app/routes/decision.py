from fastapi import APIRouter

from app.models.request_model import DecisionRequest
from app.services.ai_analytic import get_decision

router = APIRouter()


@router.post("/decision")
def decision(request: DecisionRequest):
    result = get_decision(request)
    return {"decision": result}
