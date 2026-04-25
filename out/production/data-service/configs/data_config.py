from dataclasses import dataclass


@dataclass(frozen=True)
class DataConfig:
    file_name: str = "6_Ivankov_radionuclide_activity.csv"


data_config = DataConfig()
