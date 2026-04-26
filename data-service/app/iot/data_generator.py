import numpy as np
from pandas import DataFrame


class DataGenerator:
    def __init__(self, data: DataFrame):
        self._data = data

    def generate_synthetic(self,n_samples: int = 1000) -> DataFrame:
        if self._data is None:
            raise ValueError("Data not loaded")

        sampled = self._data.sample(n=n_samples, replace=True)
        numeric_cols = sampled.columns.tolist()
        synthetic = sampled[numeric_cols].copy()

        for col in numeric_cols:
            noise = np.maximum(np.abs(synthetic[col]) * 0.10, 0.001)
            synthetic[col] = np.random.normal(loc=synthetic[col], scale=noise)
            synthetic[col] = synthetic[col].clip(lower=0).round(3)

        return synthetic
