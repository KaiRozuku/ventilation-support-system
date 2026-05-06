from fastapi import FastAPI
from app.routes.analytic_routes import router as test_router
from core.lifespan import lifespan

app = FastAPI(
    title="Ventilation Analytic System",
    lifespan=lifespan
)
# main.py

app.include_router(test_router)
