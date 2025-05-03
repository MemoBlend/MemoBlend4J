import json
import os
import sys
from pathlib import Path

# analysisapi パッケージのパスを通す
sys.path.append(str(Path(__file__).parent.parent.resolve()))
from analysisapi.scheduleplanner.web.controller import app 

# OpenAPI仕様書の出力先
file_path = "./api-docs/analysisapi/api-specification.json"

def main():
  os.makedirs(os.path.dirname(file_path), exist_ok=True)
  with open(file_path, "w", encoding="utf-8") as f:
    api_spec = app.openapi()
    f.write(json.dumps(api_spec, indent=2, ensure_ascii=False))