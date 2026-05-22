# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Suica Logger System — Android端末でSuicaをNFC読み取りし、REST APIサーバー経由でExcel Onlineに記録するシステム。

## Architecture

```
Android (NFC/FeliCa 読み取り) → REST API サーバー → Excel Online (Microsoft Graph API)
```

- **Android app** (Kotlin): NFC/FeliCa でSuica履歴を読み取り、JSONでサーバーに送信
- **API server**: ハッシュID生成・重複チェック・外出先推定・Excel書き込みを担当
- **Excel Online**: Microsoft Graph API 経由でデータ保存

## Development Rules

### Specifications
- 仕様はCLAUDE.mdに書かない。`docs/` 配下に専用ドキュメントを作成する。
- 実装前に仕様を決定し、ドキュメントに記録してから実装に着手する。

### Comments
- コードコメントは **Why not** を書く。なぜその実装を選び、何を却下したかを記述する。

### Commits
- コミットは文脈ごとに小さく区切る。
- コミットメッセージには **Why**（なぜその変更をしたか）を記述する。
