# デバッグガイド

## アプリが一瞬で閉じる問題の解決方法

### 1. Logcatでエラーログを確認

Android Studioの下部にある「Logcat」タブを開いて、エラーメッセージを確認してください。

**手順：**
1. Android Studioの下部にある「Logcat」タブをクリック
2. フィルターで「Error」を選択
3. アプリを実行して、エラーメッセージを確認

### 2. よくあるエラーと解決方法

#### エラー: `android.view.InflateException`
**原因:** レイアウトファイルの読み込みエラー
**解決方法:**
- レイアウトファイルのXML構文を確認
- リソースIDの参照が正しいか確認

#### エラー: `ClassNotFoundException`
**原因:** クラスが見つからない
**解決方法:**
- パッケージ名が正しいか確認
- クラス名のタイポを確認

#### エラー: `ResourceNotFoundException`
**原因:** リソースが見つからない
**解決方法:**
- `strings.xml`、`colors.xml`などのリソースファイルを確認
- リソースIDの参照が正しいか確認

#### エラー: `Theme not found`
**原因:** テーマが見つからない
**解決方法:**
- `themes.xml`が正しく配置されているか確認
- テーマ名のタイポを確認

### 3. デバッグ手順

1. **プロジェクトをクリーン**
   ```
   Build → Clean Project
   ```

2. **プロジェクトをリビルド**
   ```
   Build → Rebuild Project
   ```

3. **Gradleの同期**
   ```
   File → Sync Project with Gradle Files
   ```

4. **キャッシュをクリア**
   ```
   File → Invalidate Caches / Restart
   → Invalidate and Restart
   ```

### 4. エラーログの確認方法

**adbコマンドを使用する場合:**
```bash
adb logcat | grep -i error
```

**特定のアプリのログのみ表示:**
```bash
adb logcat | grep com.transportcompany.app
```

### 5. 現在の修正内容

以下の修正を行いました：

1. **LoginActivityにエラーハンドリングを追加**
   - try-catchブロックでエラーをキャッチ
   - Logcatにエラーを出力

2. **レイアウトファイルの制約を修正**
   - `titleText`の制約参照を修正

3. **テーマを修正**
   - Material Componentsテーマを使用

### 6. 次のステップ

1. アプリを再ビルドして実行
2. Logcatでエラーメッセージを確認
3. エラーメッセージの内容を共有してください

