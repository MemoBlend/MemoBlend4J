# analysisapi の使用方法

## 環境構築手順
1. analysisapiフォルダに移動
    ```
    cd analysisapi
    ```

2. 仮想環境を作成・アクティベート
  - Mac/Linuxの場合
    ```
    python -m venv venv
    source venv/bin/activate
    ```

   - Windowsの場合
     ```
     python -m venv venv
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

2. 仮想環境のアクティベート
  - Mac/Linuxの場合
    ```
    source venv/bin/activate
    ```

   - Windowsの場合
     ```
     venv\Scripts\activate
     ```

3. analysisapiパッケージを起動
    ```
    python -m analysisapi
    ```

## API定義書作成手順 
1. analysisapiフォルダに移動
    ```
    cd analysisapi
    ```

2. 仮想環境のアクティベート
  - Mac/Linuxの場合
    ```
    source venv/bin/activate
    ```

   - Windowsの場合
     ```
     venv\Scripts\activate
     ```

3. generateopenapiパッケージを起動
    ```
    python -m generateopenapi
    ```