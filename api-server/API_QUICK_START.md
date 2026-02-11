# APIサーバー クイックスタート

## セットアップ手順

### 1. Gradle Wrapperのセットアップ

APIサーバーディレクトリで以下を実行：

```bash
cd api-server
gradle wrapper --gradle-version=8.5
```

### 2. サーバーの起動

```bash
./gradlew bootRun
```

Windowsの場合：
```bash
gradlew.bat bootRun
```

### 3. APIのテスト

#### ログイン
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

#### 配送一覧取得
```bash
curl http://localhost:8080/api/deliveries
```

#### 配送詳細取得
```bash
curl http://localhost:8080/api/deliveries/1
```

#### 配送状況更新
```bash
curl -X PUT http://localhost:8080/api/deliveries/1/status \
  -H "Content-Type: application/json" \
  -d '{"status":"IN_TRANSIT"}'
```

## Androidアプリとの連携

Androidアプリの `Constants.kt` を以下のように更新：

```kotlin
const val BASE_URL = "http://10.0.2.2:8080/api/"  // エミュレーターの場合
// const val BASE_URL = "http://192.168.x.x:8080/api/"  // 実機の場合（PCのIPアドレス）
```

実機でテストする場合：
1. PCとAndroid端末を同じWi-Fiネットワークに接続
2. PCのIPアドレスを確認（例：`ipconfig` (Windows) または `ifconfig` (Mac/Linux)）
3. `BASE_URL`をPCのIPアドレスに設定

