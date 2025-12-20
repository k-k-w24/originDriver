# 運送会社荷物システム APIサーバー

運送会社向けの荷物管理システムのバックエンドAPIサーバーです。

## 技術スタック

- **フレームワーク**: Spring Boot 3.2.0
- **言語**: Kotlin 1.9.20
- **データベース**: H2 Database (開発用)
- **ビルドツール**: Gradle

## 必要な環境

- JDK 17 以上
- Gradle 8.2 以上

## 起動方法

### 1. プロジェクトのビルド

```bash
cd api-server
./gradlew build
```

### 2. サーバーの起動

```bash
./gradlew bootRun
```

または、IDE（IntelliJ IDEA、Eclipse等）から `TransportApiApplication.kt` を実行

### 3. サーバーの確認

- APIサーバー: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:transportdb`
  - ユーザー名: `sa`
  - パスワード: (空白)

## APIエンドポイント

### 認証API

#### POST /api/auth/login
ログイン認証

**リクエストボディ:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**レスポンス:**
```json
{
  "success": true,
  "message": "ログイン成功",
  "token": "base64エンコードされたトークン",
  "user": {
    "id": 1,
    "username": "admin",
    "name": "管理者",
    "role": "ADMIN"
  }
}
```

### 配送API

#### GET /api/deliveries
配送一覧を取得

**レスポンス:**
```json
[
  {
    "id": 1,
    "packageNumber": "PKG-2025-001",
    "recipientName": "山田太郎",
    "deliveryAddress": "東京都渋谷区1-2-3",
    "status": "PENDING",
    "deliveryDate": "2025-01-01T10:00:00",
    "driverName": "佐藤一郎",
    "phoneNumber": "090-1234-5678",
    "notes": null,
    "createdAt": "2025-01-01T09:00:00",
    "updatedAt": "2025-01-01T09:00:00"
  }
]
```

#### GET /api/deliveries/{id}
配送詳細を取得

**パラメータ:**
- `id`: 配送ID

#### PUT /api/deliveries/{id}/status
配送状況を更新

**リクエストボディ:**
```json
{
  "status": "IN_TRANSIT"
}
```

**ステータス値:**
- `PENDING` - 未配送
- `IN_TRANSIT` - 配送中
- `DELIVERED` - 配送完了
- `FAILED` - 配送失敗

#### GET /api/deliveries/search?query={検索文字列}
配送を検索

**クエリパラメータ:**
- `query`: 検索文字列（荷物番号、受取人名、住所で検索）

## 初期データ

サーバー起動時に以下の初期データが作成されます：

### ユーザー

1. **管理者**
   - ユーザー名: `admin`
   - パスワード: `admin123`
   - ロール: ADMIN

2. **ドライバー**
   - ユーザー名: `driver1`
   - パスワード: `driver123`
   - ロール: DRIVER

### 配送データ

4件のサンプル配送データが作成されます。

## データベース設定

開発環境ではH2 Database（インメモリ）を使用しています。

本番環境では、`application.properties`を編集してPostgreSQLやMySQLなどのデータベースに変更してください。

```properties
# PostgreSQLの例
spring.datasource.url=jdbc:postgresql://localhost:5432/transportdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## CORS設定

現在、すべてのオリジンからのアクセスを許可しています。本番環境では適切に設定してください。

## セキュリティ

現在の実装は簡易的な認証です。本番環境では以下の実装を推奨します：

- パスワードのハッシュ化（BCrypt等）
- JWT（JSON Web Token）による認証
- HTTPSの使用
- APIキー認証

## プロジェクト構造

```
api-server/
├── src/
│   ├── main/
│   │   ├── kotlin/com/transportcompany/api/
│   │   │   ├── TransportApiApplication.kt
│   │   │   ├── config/
│   │   │   │   ├── CorsConfig.kt
│   │   │   │   └── DataInitializer.kt
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.kt
│   │   │   │   └── DeliveryController.kt
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.kt
│   │   │   │   ├── LoginResponse.kt
│   │   │   │   └── UpdateStatusRequest.kt
│   │   │   ├── model/
│   │   │   │   ├── Delivery.kt
│   │   │   │   └── User.kt
│   │   │   ├── repository/
│   │   │   │   ├── DeliveryRepository.kt
│   │   │   │   └── UserRepository.kt
│   │   │   └── service/
│   │   │       ├── AuthService.kt
│   │   │       └── DeliveryService.kt
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── build.gradle.kts
```

## テスト

```bash
./gradlew test
```

## トラブルシューティング

### ポート8080が使用中の場合

`application.properties`でポート番号を変更してください：

```properties
server.port=8081
```

### データベース接続エラー

H2 Consoleに接続できない場合は、`application.properties`で以下を確認してください：

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## ライセンス

このプロジェクトは運送会社向けの内部APIサーバーです。

