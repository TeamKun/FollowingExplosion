# FollowingExplosion

一定の間隔で、ランダムあるいは指定されたプレイヤーの、過去に存在した位置で、爆発が発生するプラグイン。

## 動作環境

- Minecraft 1.16.5
- PaperMC 1.16.5

## コマンド

- fe
    - setPlayer <Player> 爆発対象プレイヤーを指定する。@a、@r可。
    - removePlayer <Player> 爆発対象プレイヤーの指定を外す。@a可。
    - mode
        - assign 爆発対象プレイヤーを指定するモードに設定する。
        - random 爆発対象プレイヤーが一定間隔ごとにランダムで設定されるモードに設定する。
        - resume プラグインの実行を再開する。
        - stop プラグインの実行を中断する。（位置取得、爆発処理、ランダム設定、すべてが中断される。）
    - config
        - display 各種config設定値の閲覧。
        - explosionPower <number> 爆発範囲を設定する。デフォルトは3。
        - explosionInterval <number> 爆発間隔を設定する。デフォルトは30。
        - locationInterval <number> 指定時間前の過去位置を爆発させる際の指定時間を設定する。デフォルトは100。
        - randomPersons <number> ランダム設定モードにおいて、一定間隔ごとに爆発対象としてランダムに設定されるプレイヤーの人数を設定する。デフォルトは1。
        - randomInterval <number> ランダム設定モードにおいて、爆発対象プレイヤーがランダムに設定される際の間隔を設定する。デフォルトは300。

## その他仕様

- 現在指定されているプレイヤーには発光エフェクトが付与される。
- モード切替時、プレイヤーの指定は全解除される。
- 爆発を停止させるときは、
    - /fe mode stopを実行
    - プレイヤー指定モードで/fe removePlayer @aを実行
    - /fe mode assignを実行（すでにプレイヤー指定モードであっても、モード切替扱いでプレイヤーの指定は全解除される。）
- 全てのconfigコマンドで受け付ける数値は1以上の整数。
- locationInterval >
  explosionIntervalの場合、計測開始時点（ランダムモード含めユーザー指定時）からの経過Tick数がlocationIntervalに到達以降の爆発処理のタイミングまで爆発処理は実行されない。
    - locationInterval=60Tick、explosionInterval=30Tickの場合、初回の爆発処理のタイミングでは60Tick前の位置情報が存在しないので、爆発処理は実行されない。
- ランダム指定モードでは、同じプレイヤーが続けて指定されたとしても、一度解除されて指定されたという扱いにしているため、ランダム設定実行時点から改めて位置取得が始まる。