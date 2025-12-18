# 運送会社荷物システム Androidアプリ

運送会社向けの荷物管理システムAndroidアプリケーションです。

## 必要な環境

- Android Studio Hedgehog (2023.1.1) 以降
- JDK 8 以降
- Android SDK (API Level 24 以上)
- Gradle 8.2 以降

## プロジェクトの立ち上げ方法

### 1. Android Studioでプロジェクトを開く

1. Android Studioを起動します
2. 「Open」または「File」→「Open」を選択します
3. プロジェクトフォルダ `AppOrigin_202512` を選択します
4. 「Trust Project」をクリックしてプロジェクトを信頼します

### 2. Gradleの同期

プロジェクトを開くと自動的にGradleの同期が開始されます。もし自動で開始されない場合は：

- 「File」→「Sync Project with Gradle Files」を選択
- または、画面上部の「Sync Now」をクリック

### 3. 依存関係のダウンロード

初回起動時は、必要なライブラリが自動的にダウンロードされます。完了まで数分かかる場合があります。

### 4. エミュレーターまたは実機の準備

#### エミュレーターを使用する場合

1. Android Studioの上部メニューから「Tools」→「Device Manager」を選択
2. 「Create Device」をクリック
3. デバイスを選択（例：Pixel 5）
4. システムイメージを選択（API Level 24以上、推奨：API 34）
5. 「Finish」をクリックしてエミュレーターを作成

#### 実機を使用する場合

1. Android端末の「設定」→「開発者向けオプション」を有効化
2. 「USBデバッグ」を有効化
3. USBケーブルでPCと接続
4. 端末に表示される「USBデバッグを許可しますか？」で「許可」を選択

### 5. アプリの実行

1. Android Studioの上部ツールバーで実行設定を確認
   - 実行対象：`app`
   - デバイス：エミュレーターまたは接続された実機
2. 緑色の「Run」ボタン（▶）をクリック
   - または、`Shift + F10`（Windows/Linux）
   - または、`Control + R`（Mac）

### 6. ビルドのみ実行する場合

- 「Build」→「Make Project」を選択
- または、`Ctrl + F9`（Windows/Linux）、`Cmd + F9`（Mac）

## アプリの使用方法

### ログイン

1. アプリを起動するとログイン画面が表示されます
2. ユーザー名とパスワードを入力（現在は簡易認証：パスワード4文字以上で認証されます）
3. 「ログイン」ボタンをタップ

### 配送一覧

- ログイン後、配送一覧画面が表示されます
- 上部の検索バーで荷物番号、受取人名、住所を検索できます
- 各配送項目をタップすると詳細画面に遷移します
- 「更新」ボタンで一覧を再読み込みします

### 配送詳細

- 配送の詳細情報が表示されます
- 「状況更新」ボタンで配送状況を変更できます
  - 未配送
  - 配送中
  - 配送完了
  - 配送失敗

## トラブルシューティング

### Gradleの同期エラー

- 「File」→「Invalidate Caches / Restart」→「Invalidate and Restart」を実行
- インターネット接続を確認
- `gradle.properties`の設定を確認

### ビルドエラー

- 「Build」→「Clean Project」を実行
- 「Build」→「Rebuild Project」を実行
- Android SDKのバージョンを確認

### エミュレーターが起動しない

- 「Tools」→「SDK Manager」で必要なシステムイメージがインストールされているか確認
- エミュレーターを再起動
- AVD（Android Virtual Device）を再作成

### 実機に接続できない

- USBデバッグが有効になっているか確認
- USBケーブルを確認
- デバイスドライバーがインストールされているか確認（Windowsの場合）

## プロジェクト構造

```
AppOrigin_202512/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/transportcompany/app/
│   │       │   ├── LoginActivity.kt
│   │       │   ├── MainActivity.kt
│   │       │   ├── DeliveryListActivity.kt
│   │       │   ├── DeliveryDetailActivity.kt
│   │       │   ├── models/
│   │       │   │   └── Delivery.kt
│   │       │   ├── adapters/
│   │       │   │   └── DeliveryAdapter.kt
│   │       │   └── utils/
│   │       │       └── Constants.kt
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   ├── values/
│   │       │   └── menu/
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 今後の拡張予定

- [ ] 実際のAPIとの連携
- [ ] 地図機能の追加（配送先の位置表示）
- [ ] プッシュ通知機能
- [ ] オフライン対応
- [ ] バーコード/QRコードスキャン機能
- [ ] 配送ルート最適化機能

## ライセンス

このプロジェクトは運送会社向けの内部アプリケーションです。

