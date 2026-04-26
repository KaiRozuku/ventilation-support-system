import os

import uvicorn
from dotenv import load_dotenv

load_dotenv()
if __name__ == "__main__":
    uvicorn.run(
        "app.main:app",
        port=int(os.getenv("PORT")),
        reload=True
    )
