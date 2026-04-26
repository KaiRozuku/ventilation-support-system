from fastapi import FastAPI

from app.routes.data_routes import router as data_router
from app.routes.iot_routes import router as iot_router
from app.routes.producer_routes import router as producer_router
from app.core.lifespan import lifespan

app = FastAPI(
    title="Ventilation Support System",
    lifespan=lifespan
)

# routers
app.include_router(data_router)
app.include_router(iot_router)
app.include_router(producer_router)
