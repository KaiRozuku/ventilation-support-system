from fastapi import FastAPI
from app.routes.decision import router

app = FastAPI(title="AI Decision Microservice")

app.include_router(router, prefix="/api")

@app.get("/health")
def health():
    return {"status": "OK"}