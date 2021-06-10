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
    - config
        - display 各種config設定値の閲覧。
        - explosionPower <number> 爆発範囲を設定する。
        - explosionInterval <number> 爆発間隔を設定する。
        - locationInterval <number> 指定時間前の過去位置を爆発させる際の指定時間を設定する。
        - randomPersons <number> ランダム設定モードにおいて、一定間隔ごとに爆発対象としてランダムに設定されるプレイヤーの人数を設定する。
        - randomInterval <number> ランダム設定モードにおいて、爆発対象プレイヤーがランダムに設定される際の間隔を設定する。
    
## その他仕様
- モード切替時、プレイヤーの指定は全解除される。
- 爆発を停止させるときは、プレイヤー指定モードで/fe removePlayer @aを実行、または、ランダムモードで/fe mode assignを実行。
- 全てのconfigコマンドで受け付ける<number>は1以上の整数。