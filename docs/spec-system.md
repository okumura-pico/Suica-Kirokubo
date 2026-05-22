# システム仕様書

## 目的

Android端末でSuica履歴をNFC読み取りし、APIサーバー経由でExcel Onlineに自動記録する。

## アーキテクチャ

```
Android (NFC/FeliCa)
    │  JSON POST
    ▼
API Server
    │  Microsoft Graph API
    ▼
Excel Online (Transactions テーブル)
```

## コンポーネント概要

| コンポーネント | 役割 | 詳細仕様 |
|---|---|---|
| Android アプリ | Suica履歴読み取り・送信 | [spec-android.md](spec-android.md) |
| API サーバー | 重複チェック・推定・記録 | [spec-api.md](spec-api.md) |
| Excel Online | データ永続化 | [spec-data.md](spec-data.md) |

## データフロー

1. AndroidがSuicaをNFCタップで読み取る
2. 履歴データをJSON形式でAPIサーバーにPOST
3. サーバーがハッシュIDを生成し重複チェック（Excel参照）
4. 重複なければ外出先を推定（スケジュール参照）
5. Excel OnlineのTransactionsテーブルに1行追加

## 制約

- 同時実行なし（シングルユーザー運用）
- 月間約100件のトランザクション
- ライセンス: Internal Use Only

## 今後の拡張予定

- Outlookカレンダー連携による外出先推定の精度向上
- Power BIでの分析
- Teams / Email 通知機能
