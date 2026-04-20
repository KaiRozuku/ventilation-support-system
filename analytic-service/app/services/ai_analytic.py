def get_decision(data):
    return calculate_decision(
        data.temperature,
        data.humidity,
        data.radiation_level
    )


def calculate_decision(temp, humidity, radiation):
    if radiation > 7:
        return "EVACUATE"
    if temp > 35 and humidity < 30:
        return "INCREASE_VENTILATION"
    if humidity > 80:
        return "DEHUMIDIFY"
    return "NORMAL"
