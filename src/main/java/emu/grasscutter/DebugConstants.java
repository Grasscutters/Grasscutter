package emu.grasscutter;

public final class DebugConstants {
    public static boolean LOG_ABILITIES = false;
    public static boolean LOG_LUA_SCRIPTS = false;
    public static boolean LOG_QUEST_START = false;
    public static boolean LOG_MISSING_ABILITIES = false;
    public static boolean LOG_MISSING_LUA_SCRIPTS = false;
    public static boolean LOG_MISSING_ABILITY_HANDLERS = false;

    /**
     * WARNING: THIS IS A DANGEROUS SETTING. DO NOT ENABLE UNLESS YOU KNOW WHAT YOU ARE DOING. This
     * allows the *client* to send *ANY* token and UID pair to the server. The server will then accept
     * the token and UID pair as valid, and set the account's token to the client specified one. This
     * can allow for IMPERSONATION and HIJACKING of accounts/servers.
     */
    public static final boolean ACCEPT_CLIENT_TOKEN = false;

    private DebugConstants() {
        // Prevent instantiation.
    }
}
