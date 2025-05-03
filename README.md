# MemoBlend

![Badge: Version](https://img.shields.io/badge/version-0.1.0-blue)
![Badge: License](https://img.shields.io/badge/license-MIT-green)

## 📖 概要

MemoBlendは日記が書けるWebアプリケーションです。
テキストはもちろん、画像や映像、音声ファイルなどで日々を記録することができます。
また、記録した内容を解析して、あなたの趣味・趣向に合わせて今後の予定を提案します。

### アプリケーションアーキテクチャ

```mermaid
flowchart TD
    %% Frontend Layer
    FE["Frontend (Diary UI)"]:::frontend

    %% Backend API Subgraph
    subgraph "Backend API"
        WEB["Web Module (REST API)"]:::backend
        AC["Application Core (Business Logic)"]:::backend
        INF["Infrastructure (Persistence with MyBatis)"]:::backend
        SC["System Common (Shared Utilities)"]:::backend
    end

    %% Analysis API and Database
    ANALYSIS["Analysis API (AI Processing)"]:::analysis
    DB[(Database)]:::database

    %% Data Flow
    FE -->|"HTTP/REST"| WEB
    WEB -->|"Calls BusinessLogic"| AC
    AC -->|"Executes DomainLogic"| INF
    INF -->|"Persists Data"| DB
    AC -->|"Utilizes Shared Utils"| SC
    AC -->|"Triggers"| ANALYSIS
    ANALYSIS -->|"Returns Recommendations"| AC

    %% Click Events
    click FE "https://github.com/memoblend/memoblend4j/tree/main/frontend/diary"
    click WEB "https://github.com/memoblend/memoblend4j/tree/main/backend/web"
    click AC "https://github.com/memoblend/memoblend4j/tree/main/backend/application-core"
    click INF "https://github.com/memoblend/memoblend4j/tree/main/backend/infrastructure"
    click SC "https://github.com/memoblend/memoblend4j/tree/main/backend/system-common"
    click ANALYSIS "https://github.com/memoblend/memoblend4j/tree/main/analysisapi"
    click DB "https://github.com/memoblend/memoblend4j/tree/main/backend/infrastructure/src/main/resources"

    %% Styles
    classDef frontend fill:#A3D8F4,stroke:#333,stroke-width:2px;
    classDef backend fill:#F4A3A3,stroke:#333,stroke-width:2px;
    classDef analysis fill:#A3F4A8,stroke:#333,stroke-width:2px;
    classDef database fill:#F4E1A3,stroke:#333,stroke-width:2px;
```

## 🪛 ローカル環境でのアプリケーション実行手順

任意のディレクトリに本リポジトリをクローンして、バックエンドアプリケーションとフロントエンドアプリケーションをそれぞれ起動してください。

### バックエンドアプリケーション

#### 前提

- Java21がダウンロードされ、環境変数やJAVA_HOMEが設定されている。

#### 手順

1. backendフォルダーをvscodeで開いてください。
2. 以下のコマンドを実行してください。
   ``` terminal
   ./gradlew web:bootRunDev
   ```
3. [localhost:8080/api/diary](localhost:8080/api/diary)にアクセスし、日記の一覧のjsonが見れることを確認してください。

### フロントエンドアプリケーション

#### 前提

- node.jsがダウンロードされ、環境変数が設定されている。

#### 手順

1. frontendフォルダーをvscodeで開いてください。
2. 以下のコマンドを実行してください。
   ``` terminal
   npm install
   ```
3. 以下のコマンドを実行してください。
   ``` terminal
   npm run dev:diary
   ```
4. [localhost:5173](localhost:5173)にアクセスし、トップ画面が表示されていることを確認してください。

### AI アプリケーション

#### 前提

- requirements.txtから仮想環境が再現され、アクティベートされている。

#### 手順

1. analysisapiのディレクトリに移動してください。
   ```terminal
   cd analysisapi
   ```
2. 以下のコマンドを実行して、サーバーを起動します。
   ```terminal
   python -m scheduleplanner
   ```

## 📄 ライセンス
追記します。

## 🔗 関連リンク
追記します。
