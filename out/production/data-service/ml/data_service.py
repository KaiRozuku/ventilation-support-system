from pathlib import Path
import pandas as pd
import numpy as np
from pandas import DataFrame


class DataService:

    def __init__(self, base_path: Path):
        self.base_path = base_path
        self._data: DataFrame | None = None

    def load_and_validate_data(
            self,
            file_name: str,
            numeric_cols: list[str]
    ) -> DataFrame:

        path = self.base_path / "resource" / "ds" / file_name

        self._data = pd.read_csv(path, engine="python")

        self._data.columns = self._data.columns.str.strip()

        self._data[numeric_cols] = self._data[numeric_cols].apply(
            pd.to_numeric,
            errors="coerce"
        )

        self._data = self._data.dropna(subset=numeric_cols)

        return self._data

    def generate_synthetic(
            self,
            numeric_cols: list[str],
            n_samples: int = 1000
    ) -> DataFrame:

        if self._data is None:
            raise ValueError("Data not loaded")

        sampled = self._data.sample(n=n_samples, replace=True)

        synthetic = sampled[numeric_cols].copy()

        for col in numeric_cols:

            noise = np.maximum(
                np.abs(synthetic[col]) * 0.10,
                0.001
            )

            synthetic[col] = np.random.normal(
                loc=synthetic[col],
                scale=noise
            )

            synthetic[col] = synthetic[col].clip(lower=0).round(3)

        return synthetic
