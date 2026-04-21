import uvicorn
from fastapi import FastAPI

from app.routes import decision

app = FastAPI(title="My Architecture App")
app.include_router(decision.router)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8085, reload=True)
