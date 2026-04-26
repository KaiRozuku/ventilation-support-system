from fastapi import FastAPI

from routes import analytic_routes

app = FastAPI(
    title="Ventilation Analytic System",
    lifespan=None
)

# routers
app.include_router(analytic_routes)
app.include_router(None)
app.include_router(None)
