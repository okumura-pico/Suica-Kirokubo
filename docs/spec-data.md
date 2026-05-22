# データ仕様書

## Excel Online テーブル

テーブル名: `Transactions`

| カラム名 | 型 | 説明 | 必須 |
|---|---|---|---|
| `user_name` | 文字列 | ユーザー名 | ○ |
| `timestamp` | 日時 | 利用日時（ISO 8601） | ○ |
| `destination` | 文字列 | 外出先（Outlookカレンダーから推定、推定不能時は空文字） | ○ |
| `in_station` | 文字列 | 入場駅名 | ○ |
| `out_station` | 文字列 | 出場駅名 | ○ |
| `type` | 文字列 | 利用種別（下記参照） | ○ |
| `amount` | 整数 | 利用金額（円） | ○ |
| `balance` | 整数 | 利用後残額（円） | ○ |
| `hash_id` | 文字列 | 重複防止ハッシュ（下記参照） | ○ |

## 利用種別（type）定義値

FeliCa から取得した数値コードを以下の文字列にマッピングする。

| 値 | 意味 |
|---|---|
| `乗車` | 電車乗車（IC） |
| `バス乗車` | バス乗車（IC） |
| `物販` | 物品購入 |
| `チャージ` | 残額チャージ |
| `その他` | 上記以外 |

## ハッシュID生成

重複チェックおよびべき等性担保のため、各トランザクションに一意のIDを付与する。

```
hash_id = SHA-256(user_name + "|" + timestamp + "|" + amount + "|" + in_station + "|" + out_station)
```

- アルゴリズム: SHA-256
- フィールド区切り: `|`
- 出力形式: hex文字列（小文字、64文字）

## Microsoft Graph API 接続情報

- 認証方式: Managed Identity（Azure Functions のシステム割り当てマネージドID）
- 対象リソース: `https://graph.microsoft.com`
- テナントID / クライアントID / ファイルパス: 環境変数で管理（コードに埋め込まない）
