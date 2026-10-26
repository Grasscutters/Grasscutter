![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [HI](README_hn-IN.md)


**Attention:** 私たちはプロジェクトへのコントリビュータをいつでも歓迎します。コントリビュートする前に、私たちの [行動規範](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md)をよくお読みください。

## 現在実装されている機能

* ログイン
* 戦闘
* フレンドリスト
* テレポート
* 祈願 (ガチャ)
* マルチプレイ (一部)
* コンソールを通したモンスターのスポーン 
* インベントリ機能 (アイテム/キャラクターの受け取り､アイテム/キャラクターのアップグレードなど)

## かんたんセットアップガイド

**Note:** サポートが必要な場合はGrasscutterの[Discordサーバー](https://discord.gg/T5vZU6UyeG)に参加してください。

### パパっとスタートアップ

- [Java (バージョン17以降)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) を用意する
- [MongoDB Community Server](https://www.mongodb.com/try/download/community) を用意する
- ゲームバージョンがREL4.0.Xのクライアントを用意する (4.0.Xのクライアントを持っていない場合は右のリンクからダウンロード): [Github](https://github.com/JRSKelvin/GenshinRepository/blob/main/Version%204.0.0.md), [Github(毎日更新)](https://github.com/tiennm99/genshin-repository/blob/main/docs/versions/4.x.md#401), [クラウド(123云盘)](https://www.123pan.com/s/HoqUVv-U7SBA.html)
- [最新の Cultivation](https://github.com/Grasscutters/Cultivation/releases/latest)をダウンロードする。`.msi`インストーラを使ってください。
- 管理者権限を付与して Cultivation を実行した後、右上端にあるダウンロードアイコンのボタンを押す。 
- `Download All-in-One` をクリックする
- 右上端にある歯車アイコンのボタンをクリックする。
- `Game Install Path` にゲームファイルのパスを指定する。
- `Custom Java Path` に、自分が用意したJavaのパスを指定する。 (例: `C:\Program Files\Java\jdk-17\bin\java.exe`)
- その他の設定には手を付けず次の段階に進む。
- Launch の隣にある小さいボタンを押す。
- Launchボタンを押す
- 好きなユーザ名でログインする。ログインに関する設定がデフォルトの場合、パスワードは何を入れてもいい。


### ビルド

Grasscutterは依存関係とビルドの処理にGradleを使用しています。

**必要要件:**

- [Java SE Development Kit 17以降](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)
- [NodeJS](https://nodejs.org/en/download) (任意、ハンドブックの生成に必要)

##### Clone
```shell
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
```

##### Compile

**Note:** 環境によってはハンドブックの生成が失敗する場合があります。ハンドブックの生成をさせない場合は `gradlew jar` コマンドに `-PskipHandbook=1` を付け加えてください。

Windows:

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # コンパイル環境の構築
.\gradlew jar # コンパイル
```

Linux:

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # コンパイル
```

##### 手動によるハンドブックの生成

Gradleを使用する場合:
```shell
./gradlew generateHandbook
```

NPMを使用する場合:
```shell
cd src/handbook
npm install
npm run build
```


生成されたjarファイルはプロジェクトのルートフォルダにあります。

### トラブルシューティング

よく散見されるトラブルとそれに対する解決策のまとめリストや、質問し誰かの助けを得たい場合は、Grasscutterの[Discordサーバー](https://discord.gg/T5vZU6UyeG)に参加し、サポートチャンネルを参照してください。
