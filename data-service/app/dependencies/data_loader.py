from app.iot.data_loader import DataLoader


# will remove
def get_data_loader() -> DataLoader:
    return DataLoader(file_name="resources/synthetic_radionuclide_data522.csv")
