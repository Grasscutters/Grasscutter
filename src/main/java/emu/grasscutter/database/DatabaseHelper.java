package emu.grasscutter.database;

import emu.grasscutter.GameConstants;
import emu.grasscutter.database.sqlite.SqliteDatabase;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.activity.PlayerActivityData;
import emu.grasscutter.game.activity.musicgame.MusicGameBeatmap;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.battlepass.BattlePassManager;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.gacha.GachaRecord;
import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameMainQuest;

import java.util.List;

public final class DatabaseHelper {

    private static final BaseDatabase impl = new SqliteDatabase();

    public static void initialize() {
        impl.initialize();
    }

    public static Account createAccount(String username) {
        return createAccountWithUid(username, 0);
    }

    public static Account createAccountWithUid(String username, int reservedUid) {
        // Unique names only
        if (DatabaseHelper.checkIfAccountExists(username)) {
            return null;
        }

        // Make sure there are no id collisions
        if (reservedUid > 0) {
            // Cannot make account with the same uid as the server console
            if (reservedUid == GameConstants.SERVER_CONSOLE_UID) {
                return null;
            }

            if (DatabaseHelper.checkIfAccountExists(reservedUid)) {
                return null;
            }

            // Make sure no existing player already has this id.
            if (DatabaseHelper.checkIfPlayerExists(reservedUid)) {
                return null;
            }
        }

        return impl.createAccountWithUid(username, reservedUid);
    }

    @Deprecated
    public static Account createAccountWithPassword(String username, String password) {
        // Unique names only
        Account exists = DatabaseHelper.getAccountByName(username);
        if (exists != null) {
            return null;
        }

        return impl.createAccountWithPassword(username, password);
    }

    public static void saveAccount(Account account) {
        impl.saveAccount(account);
    }

    public static Account getAccountByName(String username) {
        return impl.getAccountByName(username);
    }

    public static Account getAccountByToken(String token) {
        if (token == null) return null;
        return impl.getAccountByToken(token);
    }

    public static Account getAccountBySessionKey(String sessionKey) {
        if (sessionKey == null) return null;
        return impl.getAccountBySessionKey(sessionKey);
    }

    public static Account getAccountById(String uid) {
        return impl.getAccountById(uid);
    }

    public static Account getAccountByPlayerId(int playerId) {
        return impl.getAccountByPlayerId(playerId);
    }

    public static boolean checkIfAccountExists(String name) {
        return impl.checkIfAccountExists(name);
    }

    public static boolean checkIfAccountExists(int reservedUid) {
        return impl.checkIfAccountExists(reservedUid);
    }

    public static synchronized void deleteAccount(Account target) {
        impl.deleteAccount(target);
    }

    public static List<Player> getAllPlayers() {
        return impl.getAllPlayers();
    }

    public static Player getPlayerByUid(int id) {
        return impl.getPlayerByUid(id);
    }

    @Deprecated
    public static Player getPlayerByAccount(Account account) {
        return getPlayerByAccount(account, Player.class);
    }

    public static Player getPlayerByAccount(Account account, Class<? extends Player> playerClass) {
        return impl.getPlayerByAccount(account, playerClass);
    }

    public static boolean checkIfPlayerExists(int uid) {
        return impl.checkIfPlayerExists(uid);
    }

    public static synchronized Player generatePlayerUid(Player character, int reservedId) {
        return impl.generatePlayerUid(character, reservedId);
    }

    public static synchronized int getNextPlayerId(int reservedId) {
        return impl.getNextPlayerId(reservedId);
    }

    public static void savePlayer(Player character) {
        impl.savePlayer(character);
    }

    public static void saveAvatar(Avatar avatar) {
        impl.saveAvatar(avatar);
    }

    public static List<Avatar> getAvatars(Player player) {
        return impl.getAvatars(player);
    }

    public static void saveItem(GameItem item) {
        impl.saveItem(item);
    }

    public static boolean deleteItem(GameItem item) {
        return impl.deleteItem(item);
    }

    public static List<GameItem> getInventoryItems(Player player) {
        return impl.getInventoryItems(player);
    }

    public static List<Friendship> getFriends(Player player) {
        return impl.getFriends(player);
    }

    public static List<Friendship> getReverseFriends(Player player) {
        return impl.getReverseFriends(player);
    }

    public static void saveFriendship(Friendship friendship) {
        impl.saveFriendship(friendship);
    }

    public static void deleteFriendship(Friendship friendship) {
        impl.deleteFriendship(friendship);
    }

    public static Friendship getReverseFriendship(Friendship friendship) {
        return impl.getReverseFriendship(friendship);
    }

    public static List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType) {
        return getGachaRecords(ownerId, page, gachaType, 10);
    }

    public static List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType, int pageSize) {
        return impl.getGachaRecords(ownerId, page, gachaType, pageSize);
    }

    public static long getGachaRecordsMaxPage(int ownerId, int page, int gachaType) {
        return getGachaRecordsMaxPage(ownerId, page, gachaType, 10);
    }

    public static long getGachaRecordsMaxPage(int ownerId, int page, int gachaType, int pageSize) {
        return impl.getGachaRecordsMaxPage(ownerId, page, gachaType, pageSize);
    }

    public static void saveGachaRecord(GachaRecord gachaRecord) {
        impl.saveGachaRecord(gachaRecord);
    }

    public static List<Mail> getAllMail(Player player) {
        return impl.getAllMail(player);
    }

    public static void saveMail(Mail mail) {
        impl.saveMail(mail);
    }

    public static boolean deleteMail(Mail mail) {
        return impl.deleteMail(mail);
    }

    public static List<GameMainQuest> getAllQuests(Player player) {
        return impl.getAllQuests(player);
    }

    public static void saveQuest(GameMainQuest quest) {
        impl.saveQuest(quest);
    }

    public static boolean deleteQuest(GameMainQuest quest) {
        return impl.deleteQuest(quest);
    }

    public static GameHome getHomeByUid(int id) {
        return impl.getHomeByUid(id);
    }

    public static void saveHome(GameHome gameHome) {
        impl.saveHome(gameHome);
    }

    public static BattlePassManager loadBattlePass(Player player) {
        BattlePassManager manager = impl.loadBattlePass(player);
        if (manager == null) {
            manager = new BattlePassManager(player);
            manager.save();
        } else {
            manager.setPlayer(player);
        }
        return manager;
    }

    public static void saveBattlePass(BattlePassManager manager) {
        impl.saveBattlePass(manager);
    }

    public static PlayerActivityData getPlayerActivityData(int uid, int activityId) {
        return impl.getPlayerActivityData(uid, activityId);
    }

    public static void savePlayerActivityData(PlayerActivityData playerActivityData) {
        impl.savePlayerActivityData(playerActivityData);
    }

    public static MusicGameBeatmap getMusicGameBeatmap(long musicShareId) {
        return impl.getMusicGameBeatmap(musicShareId);
    }

    public static void saveMusicGameBeatmap(MusicGameBeatmap musicGameBeatmap) {
        impl.saveMusicGameBeatmap(musicGameBeatmap);
    }
}
