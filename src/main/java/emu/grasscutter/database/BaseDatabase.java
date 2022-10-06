package emu.grasscutter.database;

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

public interface BaseDatabase {
    void initialize();

    Account createAccountWithUid(String username, int reservedUid);
    Account createAccountWithPassword(String username, String password);
    void saveAccount(Account account);
    Account getAccountByName(String username);
    Account getAccountByToken(String token);
    Account getAccountBySessionKey(String sessionKey);
    Account getAccountById(String uid);
    Account getAccountByPlayerId(int playerId);
    boolean checkIfAccountExists(String name);
    boolean checkIfAccountExists(int reservedUid);
    void deleteAccount(Account target);

    List<Player> getAllPlayers();
    Player getPlayerByUid(int id);
    Player getPlayerByAccount(Account account, Class<? extends Player> playerClass);
    boolean checkIfPlayerExists(int uid);
    Player generatePlayerUid(Player character, int reservedId);
    int getNextPlayerId(int reservedId);
    void savePlayer(Player character);

    void saveAvatar(Avatar avatar);
    List<Avatar> getAvatars(Player player);

    void saveItem(GameItem item);
    boolean deleteItem(GameItem item);
    List<GameItem> getInventoryItems(Player player);

    List<Friendship> getFriends(Player player);
    List<Friendship> getReverseFriends(Player player);
    void saveFriendship(Friendship friendship);
    void deleteFriendship(Friendship friendship);
    Friendship getReverseFriendship(Friendship friendship);
    List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType, int pageSize);
    long getGachaRecordsMaxPage(int ownerId, int page, int gachaType, int pageSize);
    void saveGachaRecord(GachaRecord gachaRecord);

    List<Mail> getAllMail(Player player);
    void saveMail(Mail mail);
    boolean deleteMail(Mail mail);

    List<GameMainQuest> getAllQuests(Player player);
    void saveQuest(GameMainQuest quest);
    boolean deleteQuest(GameMainQuest quest);

    GameHome getHomeByUid(int id);
    void saveHome(GameHome gameHome);

    BattlePassManager loadBattlePass(Player player);
    void saveBattlePass(BattlePassManager manager);

    PlayerActivityData getPlayerActivityData(int uid, int activityId);
    void savePlayerActivityData(PlayerActivityData playerActivityData);

    MusicGameBeatmap getMusicGameBeatmap(long musicShareId);
    void saveMusicGameBeatmap(MusicGameBeatmap musicGameBeatmap);

}
