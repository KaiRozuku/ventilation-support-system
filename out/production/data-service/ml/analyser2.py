from pathlib import Path

import numpy as np
import pandas as pd

from sklearn.model_selection import train_test_split
from sklearn.metrics import (
    classification_report,
    accuracy_score,
    confusion_matrix
)

from sklearn.preprocessing import LabelEncoder
from xgboost import XGBClassifier

# ============================================
# 1. LOAD DATA
# ============================================

BASE_DIR = Path(__file__).resolve()
PROJECT_ROOT = BASE_DIR.parents[2]

DS = PROJECT_ROOT / "resource" / "ds"

FILE = DS / "6_Ivankov_radionuclide_activity.csv"

df = pd.read_csv(FILE, engine="python")


df.columns = (
    df.columns
    .str.strip()
    .str.replace("/", "_", regex=False)
    .str.replace(":", "_", regex=False)
)

print("\n=== COLUMNS ===")
print(df.columns.tolist())


NUMERIC_COLS = [
    "Full_weight_kg",
    "Sample_weight_kg",

    "137Cs_Bq_kg",
    "90Sr_Bq_kg",
    "40K_Bq_kg",
    "226Ra_Bq_kg",
    "232Th_Bq_kg",

    "137Cs_kBq_m2",
    "90Sr_kBq_m2",

    "Relative_uncertainty_137Cs_%",
    "Relative_uncertainty_90Sr_%",
    "Relative_uncertainty_40K_%",
    "Relative_uncertainty_226Ra_%",
    "Relative_uncertainty_232Th_%",

    "Fraction_90Sr_recovered",

    "Ratio_Cs_Sr"
]

for col in NUMERIC_COLS:

    if col in df.columns:
        df[col] = (
            df[col]
            .astype(str)
            .str.replace("<", "", regex=False)
            .str.strip()
        )

        df[col] = pd.to_numeric(df[col], errors="coerce")

CORE_COLS = [
    "137Cs_Bq_kg",
    "90Sr_Bq_kg",
    "40K_Bq_kg",
    "226Ra_Bq_kg",
    "232Th_Bq_kg"
]

df = df.dropna(subset=CORE_COLS)

print("\nDATASET SIZE:", len(df))


df["total_activity"] = (
        df["137Cs_Bq_kg"] +
        df["90Sr_Bq_kg"] +
        df["40K_Bq_kg"] +
        df["226Ra_Bq_kg"] +
        df["232Th_Bq_kg"]
)

# weighted radiation index
df["radiation_index"] = (
        0.45 * df["137Cs_Bq_kg"] +
        0.30 * df["90Sr_Bq_kg"] +
        0.10 * df["40K_Bq_kg"] +
        0.10 * df["226Ra_Bq_kg"] +
        0.05 * df["232Th_Bq_kg"]
)

# contamination density
df["contamination_density"] = (
        df["137Cs_kBq_m2"] +
        df["90Sr_kBq_m2"]
)

# uncertainty mean
df["uncertainty_mean"] = (
                                 df["Relative_uncertainty_137Cs_%"] +
                                 df["Relative_uncertainty_90Sr_%"] +
                                 df["Relative_uncertainty_40K_%"]
                         ) / 3

# activity ratio
df["cs_sr_ratio_calc"] = (
        df["137Cs_Bq_kg"] /
        (df["90Sr_Bq_kg"] + 1e-6)
)

def create_hvac_state(row):
    risk = row["radiation_index"]

    if (
            risk > 180 or
            row["137Cs_Bq_kg"] > 250 or
            row["90Sr_Bq_kg"] > 120
    ):
        return "CRITICAL"

    elif (
            risk > 90 or
            row["137Cs_Bq_kg"] > 100 or
            row["90Sr_Bq_kg"] > 40
    ):
        return "WARNING"

    else:
        return "OK"


df["hvac_state"] = df.apply(create_hvac_state, axis=1)

print("\n=== HVAC STATES ===")
print(df["hvac_state"].value_counts())


FEATURES = [

    # direct radionuclides
    "137Cs_Bq_kg",
    "90Sr_Bq_kg",
    "40K_Bq_kg",
    "226Ra_Bq_kg",
    "232Th_Bq_kg",

    # density
    "137Cs_kBq_m2",
    "90Sr_kBq_m2",

    # physical
    "Full_weight_kg",
    "Sample_weight_kg",

    # uncertainty
    "Relative_uncertainty_137Cs_%",
    "Relative_uncertainty_90Sr_%",
    "Relative_uncertainty_40K_%",

    # engineered
    "total_activity",
    "radiation_index",
    "contamination_density",
    "uncertainty_mean",
    "cs_sr_ratio_calc"
]

# keep existing cols only
FEATURES = [f for f in FEATURES if f in df.columns]

# remove NaN
df = df.dropna(subset=FEATURES)

X = df[FEATURES]
y = df["hvac_state"]


encoder = LabelEncoder()

y_encoded = encoder.fit_transform(y)

print("\n=== LABELS ===")
for i, cls in enumerate(encoder.classes_):
    print(i, "=", cls)


X_train, X_test, y_train, y_test = train_test_split(
    X,
    y_encoded,
    test_size=0.2,
    random_state=42,
    stratify=y_encoded
)


model = XGBClassifier(
    n_estimators=400,
    learning_rate=0.05,
    max_depth=5,
    subsample=0.8,
    colsample_bytree=0.8,
    eval_metric="mlogloss",
    random_state=42
)

model.fit(X_train, y_train)


preds = model.predict(X_test)

pred_labels = encoder.inverse_transform(preds)
true_labels = encoder.inverse_transform(y_test)

print("\n=== ACCURACY ===")
print(accuracy_score(true_labels, pred_labels))

print("\n=== CLASSIFICATION REPORT ===")
print(classification_report(true_labels, pred_labels))

print("\n=== CONFUSION MATRIX ===")
print(confusion_matrix(true_labels, pred_labels))

importance = pd.DataFrame({
    "feature": FEATURES,
    "importance": model.feature_importances_
})

importance = importance.sort_values(
    by="importance",
    ascending=False
)

print("\n=== FEATURE IMPORTANCE ===")
print(importance)



def predict_hvac(sample: dict) -> dict:
    data = pd.DataFrame([sample])

    # engineered features
    data["total_activity"] = (
            data["137Cs_Bq_kg"] +
            data["90Sr_Bq_kg"] +
            data["40K_Bq_kg"] +
            data["226Ra_Bq_kg"] +
            data["232Th_Bq_kg"]
    )

    data["radiation_index"] = (
            0.45 * data["137Cs_Bq_kg"] +
            0.30 * data["90Sr_Bq_kg"] +
            0.10 * data["40K_Bq_kg"] +
            0.10 * data["226Ra_Bq_kg"] +
            0.05 * data["232Th_Bq_kg"]
    )

    data["contamination_density"] = (
            data["137Cs_kBq_m2"] +
            data["90Sr_kBq_m2"]
    )

    data["uncertainty_mean"] = (
                                       data["Relative_uncertainty_137Cs_%"] +
                                       data["Relative_uncertainty_90Sr_%"] +
                                       data["Relative_uncertainty_40K_%"]
                               ) / 3

    data["cs_sr_ratio_calc"] = (
            data["137Cs_Bq_kg"] /
            (data["90Sr_Bq_kg"] + 1e-6)
    )

    X_input = data[FEATURES]

    pred = model.predict(X_input)[0]

    probabilities = model.predict_proba(X_input)[0]

    label = encoder.inverse_transform([pred])[0]

    return {
        "status": label,
        "confidence": float(np.max(probabilities)),
        "probabilities": {
            encoder.classes_[i]: float(probabilities[i])
            for i in range(len(probabilities))
        }
    }


# ============================================
# 15. TEST SAMPLE
# ============================================

sample = {

    "Full_weight_kg": 1.661,
    "Sample_weight_kg": 1.335,

    "137Cs_Bq_kg": 191,
    "90Sr_Bq_kg": 13.7,
    "40K_Bq_kg": 156,
    "226Ra_Bq_kg": 12,
    "232Th_Bq_kg": 9,

    "137Cs_kBq_m2": 62.4,
    "90Sr_kBq_m2": 4.49,

    "Relative_uncertainty_137Cs_%": 6,
    "Relative_uncertainty_90Sr_%": 12.6,
    "Relative_uncertainty_40K_%": 13
}

print("\n=== HVAC PREDICTION ===")

result = predict_hvac(sample)

print(result)
