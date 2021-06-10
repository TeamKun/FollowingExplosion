package net.kunmc.lab.followingexplosion;

public class CommandConst {
    public final static String MAIN_COMMAND = "fe";

    //第1引数　サブコマンド
    public final static String COMMAND_PLAYER_SET = "setPlayer";
    public final static String COMMAND_PLAYER_REMOVE = "removePlayer";
    public final static String COMMAND_MODE = "mode";
    public final static String COMMAND_CONFIG = "config";

    //第2引数　COMMAND_MODE
    public final static String MODE_RANDOM = "random";
    public final static String MODE_ASSIGN = "assign";

    //第2引数　COMMAND_CONFIG
    public final static String CONFIG_DISPLAY = "display";
    public final static String CONFIG_EXPLOSION_POWER = "explosionPower";
    public final static String CONFIG_EXPLOSION_INTERVAL = "explosionInterval";
    public final static String CONFIG_LOCATION_INTERVAL = "locationInterval";
    public final static String CONFIG_RANDOM_PERSONS = "randomPersons";
    public final static String CONFIG_RANDOM_INTERVAL = "randomInterval";
}
