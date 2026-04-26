from pathlib import Path
import pandas as pd
from pandas import DataFrame


class DataLoader:
    def __init__(self, file_name):
        self._path = Path(__file__).resolve().parents[2] / file_name

    def load_and_validate_data(self) -> DataFrame:
        _data = pd.read_csv(self._path, engine="python")
        _data.columns = _data.columns.str.strip()
        _data[_data.columns] = _data[_data.columns].apply(
            pd.to_numeric,
            errors="coerce"
        )
        return _data.dropna(subset=_data.columns)
