# analysisapi の使用方法

## 環境構築手順
1. analysisapiフォルダに移動
    ```
    cd analysisapi
    ```

2. 仮想環境を作成・アクティベート
  - Mac/Linuxの場合
    ```
    python -m venv <任意の名前>
    source venv/bin/activate
    ```

- Windowsの場合
  ```
  python -m venv <任意の名前>
  venv\Scripts\activate
  ```

3. requirements.txtから環境再現  
    ```
    pip install -r requirements.txt
    ```

## サーバー起動手順
1. analysisapiフォルダに移動
    ```
    cd analysisapi
    ```

2. 仮想環境の作成・アクティベート
- Mac/Linuxの場合
  ```
  python -m venv <任意の名前>
  source venv/bin/activate
  ```

- Windowsの場合
  ```
  python -m venv <任意の名前>
  venv\Scripts\activate.bat
  ```

3. requirements.txtから環境再現  
    ```
    pip install -r requirements.txt
    ```

4. analysisapiプロジェクトを起動
    ```
    python -m analysisapi
    ```