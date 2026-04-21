# =========================================================
# 🔷 UNIFIED DSS DATA FUSION (Chernobyl + Soil + HVAC)
# =========================================================

import pandas as pd
import numpy as np
from pathlib import Path

# =========================================================
# 🔷 1. PATHS
# =========================================================

BASE_DIR = Path(__file__).resolve().parents[2]
DATA_DIR = BASE_DIR / "resource"

# =========================================================
# 🔷 2. LOAD DATA
# =========================================================

soil = pd.read_csv(DATA_DIR / "data.csv")
chernobyl = pd.read_csv(DATA_DIR / "6_Ivankov_background_radiation.csv")
hvac = pd.read_csv(DATA_DIR / "HVAC_NE_EC_19-21.csv")

# clean column names
soil.columns = soil.columns.str.strip()
chernobyl.columns = chernobyl.columns.str.strip()
hvac.columns = hvac.columns.str.strip()

# =========================================================
# 🔷 3. CHERNOBYL (TIME SERIES EVENT)
# =========================================================

chernobyl["Date"] = pd.to_datetime(chernobyl["Date"], errors="coerce")

chern_log = pd.DataFrame({
    "timestamp": chernobyl["Date"],
    "lat": chernobyl["X"],
    "lon": chernobyl["Y"],
    "event_type": "radiation_air",
    "value_1": chernobyl["I_131_(Bq/m3)"],
    "value_2": chernobyl["Cs_134_(Bq/m3)"],
    "value_3": chernobyl["Cs_137_(Bq/m3)"],
    "source": "chernobyl"
})

# =========================================================
# 🔷 4. SOIL (SPATIAL ONLY - NO DATE!)
# =========================================================

soil_log = pd.DataFrame({
    "timestamp": pd.NaT,  # ❗ no time dimension
    "lat": soil["latitude"],
    "lon": soil["longitude"],
    "event_type": "soil_radiation",
    "value_1": soil["at_1m"],
    "value_2": soil["at_0.1m"],
    "value_3": np.nan,
    "source": "soil"
})

# =========================================================
# 🔷 5. HVAC (SYSTEM TELEMETRY)
# =========================================================

hvac["Timestamp"] = pd.to_datetime(hvac["Timestamp"], errors="coerce")

hvac_log = pd.DataFrame({
    "timestamp": hvac["Timestamp"],
    "lat": np.nan,
    "lon": np.nan,
    "event_type": "hvac_system",
    "value_1": hvac["Energy"],
    "value_2": hvac["T_Outdoor"],
    "value_3": hvac["RH_Outdoor"],
    "source": "hvac"
})

# =========================================================
# 🔷 6. MERGE INTO EVENT STREAM (LOG DATASET)
# =========================================================

final_logs = pd.concat(
    [chern_log, soil_log, hvac_log],
    ignore_index=True
)

# =========================================================
# 🔷 7. CLEANING + FEATURE ENGINEERING
# =========================================================

final_logs["value_1"] = final_logs["value_1"].fillna(0)
final_logs["value_2"] = final_logs["value_2"].fillna(0)
final_logs["value_3"] = final_logs["value_3"].fillna(0)

# DSS risk score (simple fusion heuristic)
final_logs["risk_score"] = (
        final_logs["value_1"] * 0.5 +
        final_logs["value_2"] * 0.3 +
        final_logs["value_3"] * 0.2
)

# sort as real event stream
final_logs = final_logs.sort_values("timestamp")

# =========================================================
# 🔷 8. OPTIONAL: DATABASE-READY EXPORT
# =========================================================

output_path = BASE_DIR / "unified_event_logs.csv"
final_logs.to_csv(output_path, index=False)

print("✅ DSS unified event dataset created:", output_path)
print(final_logs.head())