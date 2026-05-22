# データ仕様書

## Excel Online テーブル

テーブル名: `Transactions`

| カラム名 | 型 | 説明 | 必須 |
|---|---|---|---|
| `user_name` | 文字列 | ユーザー名 | ○ |
| `timestamp` | 日時 | 利用日時（ISO 8601） | ○ |
| `destination` | 文字列 | 外出先（推定値、TBD: 推定不能時の値） | ○ |
| `in_station` | 文字列 | 入場駅名 | ○ |
| `out_station` | 文字列 | 出場駅名 | ○ |
| `type` | 文字列 | 利用種別（TBD: 定義値一覧） | ○ |
| `amount` | 整数 | 利用金額（円） | ○ |
| `balance` | 整数 | 利用後残額（円） | ○ |
| `hash_id` | 文字列 | 重複防止ハッシュ（下記参照） | ○ |

## ハッシュID生成

重複チェックおよびべき等性担保のため、各トランザクションに一意のIDを付与する。

```
hash_id = hash(user_name + timestamp + amount + in_station + out_station)
```

- ハッシュアルゴリズム: TBD（SHA-256推奨）
- 文字列結合の区切り文字: TBD
- 出力形式: TBD（hex文字列など）

## Microsoft Graph API 接続情報

- TBD: テナントID、クライアントID、対象ファイルのパス
- TBD: 認証方式（アプリ登録によるクライアントクレデンシャルフロー想定）
