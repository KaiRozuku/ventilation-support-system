# import ollama
# import json
#
# # =========================
# # HVAC SENSOR DATA
# # =========================
# sensor_data = {
#     "temperature": 31.4,
#     "humidity": 78,
#     "co2": 1450,
#     "pressure": 210,
#     "power": 8.2,
#     "fan_speed": 40,
#     "vibration": 0.82,
#     "airflow": 320
# }
#
# # =========================
# # ML / AI PREDICTIONS
# # =========================
# ml_results = {
#     "anomaly_probability": 0.87,
#     "predicted_failure": "compressor",
#     "risk_level": "high"
# }
#
# # =========================
# # SYSTEM RULES / THRESHOLDS
# # =========================
# thresholds = {
#     "max_temperature": 28,
#     "max_humidity": 70,
#     "max_co2": 1000,
#     "max_pressure": 180
# }
#
# # =========================
# # PROMPT
# # =========================
# prompt = f"""
# You are an advanced HVAC Decision Support System.
#
# Your task:
# Analyze telemetry, detect problems, estimate risk,
# and generate professional recommendations.
#
# IMPORTANT RULES:
# - Return ONLY valid JSON
# - No markdown
# - No explanations outside JSON
# - Response language: Ukrainian
# - Maximum 100 words
#
# Status rules:
# - OK -> normal operation
# - WARNING -> moderate issue
# - CRITICAL -> dangerous condition
#
# Required JSON format:
#
# {{
#   "status": "OK | WARNING | CRITICAL",
#   "problem": "main issue",
#   "interpretation": "technical explanation",
#   "recommendation": "recommended action",
#   "risk_level": "low | medium | high"
# }}
#
# HVAC telemetry:
# {json.dumps(sensor_data, indent=2)}
#
# ML predictions:
# {json.dumps(ml_results, indent=2)}
#
# Thresholds:
# {json.dumps(thresholds, indent=2)}
# """
#
# # =========================
# # OLLAMA REQUEST
# # =========================
# stream = ollama.chat(
#     model="phi3:mini",
#     messages=[
#         {
#             "role": "system",
#             "content": (
#                 "You are a professional HVAC AI assistant "
#                 "for industrial decision support systems."
#             )
#         },
#         {
#             "role": "user",
#             "content": prompt
#         }
#     ],
#     stream=True,
#     options={
#         "temperature": 0.2,
#         "num_predict": 100,
#         "top_p": 0.9
#     }
# )
#
# # =========================
# # STREAM OUTPUT
# # =========================
# full_response = ""
#
# for chunk in stream:
#     content = chunk["message"]["content"]
#     print(content, end="", flush=True)
#     full_response += content
#
# # =========================
# # OPTIONAL JSON PARSING
# # =========================
# try:
#     result = json.loads(full_response)
#
#     print("\n\n========== PARSED RESULT ==========")
#     print("STATUS:", result["status"])
#     print("PROBLEM:", result["problem"])
#     print("RISK:", result["risk_level"])
#
# except Exception as e:
#     print("\nJSON parse error:", e)

from llama_cpp import Llama
import json
import time

class HVACPipeline:
    def __init__(self):
        self.model = Llama(
            model_path="phi3-mini.Q4_K_M.gguf",
            n_threads=8,
            n_ctx=512,
            n_batch=512
        )

    def preprocess(self, data):
        # мінімізація токенів (важливо!)
        return {
            "t": data["temperature"],
            "h": data["humidity"],
            "c": data["co2"]
        }

    def run(self, data):
        compact = self.preprocess(data)

        prompt = f"{compact} → JSON(status,problem,action)"

        out = self.model(
            prompt,
            max_tokens=50,
            temperature=0.1
        )

        return out["choices"][0]["text"]

# -----------------------
pipe = HVACPipeline()

sensor_data = {
    "temperature": 31.4,
    "humidity": 78,
    "co2": 1450
}

start = time.time()
result = pipe.run(sensor_data)
print(result)
print("latency:", time.time() - start)