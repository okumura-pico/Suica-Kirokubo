# API サーバー仕様書

## 概要

- 役割: Suicaトランザクションの受信、重複排除、外出先推定、Excel Online書き込み
- 実装言語/フレームワーク: TBD
- 認証: TBD（内部ネットワーク限定 or 簡易トークン認証）

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

- TBD: スケジュール参照方法（Outlookカレンダー or ローカル設定ファイル）
- TBD: 推定不能時のフォールバック値（空文字 or "不明" など）
- TBD: 推定ルール（時刻×駅 → 外出先のマッピング方式）
