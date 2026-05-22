# Suica Logger System

## 概要
このプロジェクトは、Android端末でSuica（交通系ICカード）を読み取り、履歴をサーバーへ送信し、Excel Onlineに記録するシステムです。

## アーキテクチャ

```
Android (NFC読み取り)
    ↓
APIサーバー
    ↓
Excel Online (データ保存)
```

## 機能

- Suica履歴の読み取り（Android / NFC）
- サーバーへの履歴送信
- 未登録履歴の重複チェック
- スケジュールに基づく外出先推定
- Excel Onlineへの自動記録

## 記録項目

- ユーザー名
- 利用日時
- 外出先（推定）
- 入場駅
- 出場駅
- 利用種別
- 金額
- 利用後残額
- 履歴ID（重複防止）

## データフロー

1. AndroidがSuica履歴を取得
2. JSON形式でサーバーに送信
3. サーバーで以下を実行
   - ハッシュID生成
   - 重複チェック（Excel）
   - 外出先推定（スケジュール参照）
   - Excel Onlineへ追加

## 技術要素

### Android
- NFC（FeliCa）
- Kotlin

### サーバー
- REST API
- 外出先推定ロジック
- Microsoft Graph API

### データ保存
- Excel Online（テーブル形式）

## 重複チェック

履歴IDは以下の組み合わせで生成：

```
user + timestamp + amount + in_station + out_station
```

## Excel構成

テーブル名: Transactions

推奨カラム構成:

- user_name
- timestamp
- destination
- in_station
- out_station
- type
- amount
- balance
- hash_id

## 前提条件

- 同時実行なし
- 月間100件程度のデータ

## 今後の拡張案

- Outlookカレンダー連携
- Power BIでの分析
- 通知機能（Teams / Email）

## ライセンス

Internal Use Only
