from pydantic import BaseModel


class DecisionRequest(BaseModel):
    temperature: float
    humidity: float
    radiation_level: float
