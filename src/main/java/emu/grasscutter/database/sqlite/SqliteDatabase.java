package emu.grasscutter.database.sqlite;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.database.BaseDatabase;
import emu.grasscutter.database.DatabaseHelper;
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

import java.sql.SQLException;
import java.util.List;

import static emu.grasscutter.database.sqlite.MorphiaSqlite.wrapString;

public class SqliteDatabase implements BaseDatabase {

    @Override
    public void initialize() {
        try {
            MorphiaSqlite.connect("jdbc:sqlite:grasscutter.db");
            var entity = MorphiaSqlite.scanPackageEntity("emu.grasscutter");
            entity.forEach((cls) -> {
                try {
                    MorphiaSqlite.createTable(cls);
                } catch (SQLException e) {
                    Grasscutter.getLogger().error("sqlite create table failed: ", e);
                }
            });
        } catch (Exception e) {
            Grasscutter.getLogger().error("sqlite init failed: ", e);
        }
    }

    @Override
    public Account createAccountWithUid(String username, int reservedUid) {
        Account account = new Account();
        account.setUsername(username);
        if (reservedUid > 0) {
            account.setReservedPlayerUid(reservedUid);
        }
        DatabaseHelper.saveAccount(account);
        return account;
    }

    @Override
    public Account createAccountWithPassword(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        DatabaseHelper.saveAccount(account);
        return account;
    }

    @Override
    public void saveAccount(Account account) {
        MorphiaSqlite.insertOrUpdate(account);
    }

    @Override
    public Account getAccountByName(String username) {
        return MorphiaSqlite.queryFirstOrNull(Account.class, "username = " + wrapString(username));
    }

    @Override
    public Account getAccountByToken(String token) {
        return MorphiaSqlite.queryFirstOrNull(Account.class, "token = " + wrapString(token));
    }

    @Override
    public Account getAccountBySessionKey(String sessionKey) {
        return MorphiaSqlite.queryFirstOrNull(Account.class, "sessionKey = " + wrapString(sessionKey));
    }

    @Override
    public Account getAccountById(String uid) {
        return MorphiaSqlite.queryFirstOrNull(Account.class, "id = " + wrapString(uid));
    }

    @Override
    public Account getAccountByPlayerId(int playerId) {
        return MorphiaSqlite.queryFirstOrNull(Account.class, "reservedPlayerId = " + playerId);
    }

    @Override
    public boolean checkIfAccountExists(String name) {
        return getAccountByName(name) != null;
    }

    @Override
    public boolean checkIfAccountExists(int reservedUid) {
        return getAccountByPlayerId(reservedUid) != null;
    }

    @Override
    public void deleteAccount(Account target) {
        // TODO impl delete account
    }

    @Override
    public List<Player> getAllPlayers() {
        return MorphiaSqlite.query(Player.class);
    }

    @Override
    public Player getPlayerByUid(int id) {
        return MorphiaSqlite.queryFirstOrNull(Player.class, "id = " + id);
    }

    @Override
    public Player getPlayerByAccount(Account account, Class<? extends Player> playerClass) {
        return MorphiaSqlite.queryFirstOrNull(Player.class, "accountId = " + wrapString(account.getId()));
    }

    @Override
    public boolean checkIfPlayerExists(int uid) {
        return getPlayerByUid(uid) != null;
    }

    @Override
    public Player generatePlayerUid(Player character, int reservedId) {
        // Check if reserved id
        int id = 1;
        if (reservedId > 0 && !checkIfPlayerExists(reservedId)) {
            id = reservedId;
            character.setUid(id);
        } else {
            do {
                id++;
            }
            while (checkIfPlayerExists(id));
            character.setUid(id);
        }
        // Save to database
        savePlayer(character);
        return character;
    }

    @Override
    public int getNextPlayerId(int reservedId) {
        // Check if reserved id
        int id = 1;
        if (reservedId > 0 && !checkIfPlayerExists(reservedId)) {
            id = reservedId;
        } else {
            do {
                id++;
            }
            while (checkIfPlayerExists(id));
        }
        return id;
    }

    @Override
    public void savePlayer(Player character) {
        MorphiaSqlite.insertOrUpdate(character);
    }

    @Override
    public void saveAvatar(Avatar avatar) {
        MorphiaSqlite.insertOrUpdate(avatar);
    }

    @Override
    public List<Avatar> getAvatars(Player player) {
        return MorphiaSqlite.query(Avatar.class, "ownerId = " + player.getUid());
    }

    @Override
    public void saveItem(GameItem item) {
        MorphiaSqlite.insertOrUpdate(item);
    }

    @Override
    public boolean deleteItem(GameItem item) {
        return MorphiaSqlite.delete(item);
    }

    @Override
    public List<GameItem> getInventoryItems(Player player) {
        return MorphiaSqlite.query(GameItem.class, "ownerId = " + player.getUid());
    }

    @Override
    public List<Friendship> getFriends(Player player) {
        return MorphiaSqlite.query(Friendship.class, "ownerId = " + player.getUid());
    }

    @Override
    public List<Friendship> getReverseFriends(Player player) {
        return MorphiaSqlite.query(Friendship.class, "friendId = " + player.getUid());
    }

    @Override
    public void saveFriendship(Friendship friendship) {
        MorphiaSqlite.insertOrUpdate(friendship);
    }

    @Override
    public void deleteFriendship(Friendship friendship) {
        MorphiaSqlite.delete(friendship);
    }

    @Override
    public Friendship getReverseFriendship(Friendship friendship) {
        String condition = "ownerId = " + friendship.getFriendId() + " AND friendId = " + friendship.getOwnerId();
        return MorphiaSqlite.queryFirstOrNull(Friendship.class, condition);
    }

    @Override
    public List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType, int pageSize) {
        String condition = "ownerId = " + ownerId + " AND gachaType = " + gachaType +
            " ORDER BY transactionDate DESC LIMIT " + pageSize + " OFFSET " + pageSize * page;
        return MorphiaSqlite.query(GachaRecord.class, condition);
    }

    @Override
    public long getGachaRecordsMaxPage(int ownerId, int page, int gachaType, int pageSize) {
        String condition = "ownerId = " + ownerId + " AND gachaType = " + gachaType;
        int count = MorphiaSqlite.query(GachaRecord.class, condition).size();
        return count / pageSize + (count % pageSize > 0 ? 1 : 0);
    }

    @Override
    public void saveGachaRecord(GachaRecord gachaRecord) {
        MorphiaSqlite.insertOrUpdate(gachaRecord);
    }

    @Override
    public List<Mail> getAllMail(Player player) {
        return MorphiaSqlite.query(Mail.class, "ownerUid = " + player.getUid());
    }

    @Override
    public void saveMail(Mail mail) {
        MorphiaSqlite.insertOrUpdate(mail);
    }

    @Override
    public boolean deleteMail(Mail mail) {
        return MorphiaSqlite.delete(mail);
    }

    @Override
    public List<GameMainQuest> getAllQuests(Player player) {
        return MorphiaSqlite.query(GameMainQuest.class, "ownerUid = " + player.getUid());
    }

    @Override
    public void saveQuest(GameMainQuest quest) {
        MorphiaSqlite.insertOrUpdate(quest);
    }

    @Override
    public boolean deleteQuest(GameMainQuest quest) {
        return MorphiaSqlite.delete(quest);
    }

    @Override
    public GameHome getHomeByUid(int id) {
        return MorphiaSqlite.queryFirstOrNull(GameHome.class, "ownerUid = " + id);
    }

    @Override
    public void saveHome(GameHome gameHome) {
        MorphiaSqlite.insertOrUpdate(gameHome);
    }

    @Override
    public BattlePassManager loadBattlePass(Player player) {
        return MorphiaSqlite.queryFirstOrNull(BattlePassManager.class, "ownerUid = " + player.getUid());
    }

    @Override
    public void saveBattlePass(BattlePassManager manager) {
        MorphiaSqlite.insertOrUpdate(manager);
    }

    @Override
    public PlayerActivityData getPlayerActivityData(int uid, int activityId) {
        String condition = "uid = " + uid + " AND activityId = " + activityId;
        return MorphiaSqlite.queryFirstOrNull(PlayerActivityData.class, condition);
    }

    @Override
    public void savePlayerActivityData(PlayerActivityData playerActivityData) {
        MorphiaSqlite.insertOrUpdate(playerActivityData);
    }

    @Override
    public MusicGameBeatmap getMusicGameBeatmap(long musicShareId) {
        return MorphiaSqlite.queryFirstOrNull(MusicGameBeatmap.class, "musicShareId = " + musicShareId);
    }

    @Override
    public void saveMusicGameBeatmap(MusicGameBeatmap musicGameBeatmap) {
        MorphiaSqlite.insertOrUpdate(musicGameBeatmap);
    }
}
