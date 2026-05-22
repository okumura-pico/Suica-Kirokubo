# API サーバー仕様書

## 概要

- 役割: Suicaトランザクションの受信、重複排除、外出先推定、Excel Online書き込み
- 実装言語/フレームワーク: Kotlin（Azure Functions）
- 認証: Azure AD（Microsoft Entra ID）
  - Android → Functions: MSAL で取得したアクセストークンを Bearer ヘッダーに付与
  - Functions → Graph API: Managed Identity（シークレット管理不要）

## エンドポイント

### POST /transactions

Suica履歴を受信し、Excel Onlineに記録する。

**リクエスト**

```json
{
  "user_name": "string",
  "records": [
    {
      "timestamp": "2026-05-22T09:00:00+09:00",
      "in_station": "string",
      "out_station": "string",
      "type": "string",
      "amount": 200,
      "balance": 5000
    }
  ]
}
```

**レスポンス**

| ステータス | 意味 |
|---|---|
| 200 OK | 処理完了（件数・スキップ数を返す） |
| 400 Bad Request | リクエスト形式不正 |
| 500 Internal Server Error | サーバーエラー |

```json
{
  "inserted": 3,
  "skipped": 1
}
```

## 処理フロー

```
受信
  ↓
各レコードについて:
  1. ハッシュID生成
  2. 重複チェック（Excel の hash_id 列を検索）
  3. 重複あり → スキップ
  4. 重複なし → 外出先推定 → Excel 書き込み
```

## 外出先推定ロジック

Microsoft Graph API でユーザーのOutlookカレンダーを参照し、利用時刻に重なるイベントの `location` フィールドを外出先とする。

```
推定手順:
  1. Graph API で当日のカレンダーイベントを取得
  2. 出場時刻 (timestamp) に重なるイベントを検索
  3. 該当イベントの location を destination に設定
  4. 該当イベントなし or location 未設定 → destination = ""（空文字）
```

- 複数イベントが重なる場合: 開始時刻が最も近いものを優先
- 推定不能時のフォールバック: 空文字（""）
