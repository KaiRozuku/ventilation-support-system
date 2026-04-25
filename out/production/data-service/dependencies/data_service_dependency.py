from pathlib import Path

from app1.ml.data_service import DataService


def get_data_service() -> DataService:
    return DataService(
        base_path=Path(__file__).resolve().parents[2]
    )
