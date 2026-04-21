from fastapi import APIRouter

router = APIRouter(
    prefix="/users",
    tags=["Users"]
)
# router = APIRouter(prefix="/decisions", tags=["Decisions"])


@router.get("/")
def get_users() -> list[dict]:
    return [{"username": "Rick"}, {"username": "Morty"}]


@router.get("/me")
def get_current_user() -> dict:
    return {"username": "Rick", "role": "admin"}


@router.get("/")
def get_decisions() -> list[dict]:
    return [{"id": 1, "status": "active"}]
