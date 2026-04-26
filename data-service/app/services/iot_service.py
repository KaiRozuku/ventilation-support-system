from iot.data_generator import DataGenerator
from iot.data_loader import DataLoader


class IoTService:
    def __init__(self, loader: DataLoader, generator: DataGenerator):
        self.loader = loader
        self.generator = generator

    def validate_data(self):
        return {"rows": len(self.loader.load_and_validate_data())}

    def generate_synthetic(self, n_samples: int = 3000):
        data = self.generator.generate_synthetic(n_samples)
        return data.to_dict(orient="records")
