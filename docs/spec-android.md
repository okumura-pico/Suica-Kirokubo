# Android アプリ仕様書

## 概要

- プラットフォーム: Android
- 言語: Kotlin
- 最低APIレベル: TBD（NFC/FeliCa対応端末が対象）

## 機能

1. NFC（FeliCa）でSuicaをタップ読み取り
2. 読み取った履歴をAPIサーバーにPOST送信
3. 送信結果（登録件数・スキップ件数）を画面表示

## NFC 読み取り

- FeliCa対応のAndroid NFC APIを使用
- Suicaから取得するデータ:
  - 利用日時
  - 入場駅
  - 出場駅
  - 利用種別
  - 金額
  - 利用後残額
- TBD: 取得可能な最大履歴件数（Suicaカードの仕様に依存）

## API 送信

- 送信先: TBD（APIサーバーのURL・ポート）
- 方式: HTTP POST（JSON）
- TBD: 証明書検証・通信暗号化の要件
- TBD: 送信失敗時のリトライ方針

## UI

- TBD: 画面構成（シンプルな1画面 or 複数画面）
- TBD: 送信前の確認UI（件数プレビューなど）
