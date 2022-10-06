package emu.grasscutter.database.sqlite;

import emu.grasscutter.database.BaseDatabase;
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

public class SqliteDatabase implements BaseDatabase{

    @Override
    public void initialize() {

    }

    @Override
    public Account createAccountWithUid(String username, int reservedUid) {
        return null;
    }

    @Override
    public Account createAccountWithPassword(String username, String password) {
        return null;
    }

    @Override
    public void saveAccount(Account account) {

    }

    @Override
    public Account getAccountByName(String username) {
        return null;
    }

    @Override
    public Account getAccountByToken(String token) {
        return null;
    }

    @Override
    public Account getAccountBySessionKey(String sessionKey) {
        return null;
    }

    @Override
    public Account getAccountById(String uid) {
        return null;
    }

    @Override
    public Account getAccountByPlayerId(int playerId) {
        return null;
    }

    @Override
    public boolean checkIfAccountExists(String name) {
        return false;
    }

    @Override
    public boolean checkIfAccountExists(int reservedUid) {
        return false;
    }

    @Override
    public void deleteAccount(Account target) {

    }

    @Override
    public List<Player> getAllPlayers() {
        return null;
    }

    @Override
    public Player getPlayerByUid(int id) {
        return null;
    }

    @Override
    public Player getPlayerByAccount(Account account, Class<? extends Player> playerClass) {
        return null;
    }

    @Override
    public boolean checkIfPlayerExists(int uid) {
        return false;
    }

    @Override
    public Player generatePlayerUid(Player character, int reservedId) {
        return null;
    }

    @Override
    public int getNextPlayerId(int reservedId) {
        return 0;
    }

    @Override
    public void savePlayer(Player character) {

    }

    @Override
    public void saveAvatar(Avatar avatar) {

    }

    @Override
    public List<Avatar> getAvatars(Player player) {
        return null;
    }

    @Override
    public void saveItem(GameItem item) {

    }

    @Override
    public boolean deleteItem(GameItem item) {
        return false;
    }

    @Override
    public List<GameItem> getInventoryItems(Player player) {
        return null;
    }

    @Override
    public List<Friendship> getFriends(Player player) {
        return null;
    }

    @Override
    public List<Friendship> getReverseFriends(Player player) {
        return null;
    }

    @Override
    public void saveFriendship(Friendship friendship) {

    }

    @Override
    public void deleteFriendship(Friendship friendship) {

    }

    @Override
    public Friendship getReverseFriendship(Friendship friendship) {
        return null;
    }

    @Override
    public List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType, int pageSize) {
        return null;
    }

    @Override
    public long getGachaRecordsMaxPage(int ownerId, int page, int gachaType, int pageSize) {
        return 0;
    }

    @Override
    public void saveGachaRecord(GachaRecord gachaRecord) {

    }

    @Override
    public List<Mail> getAllMail(Player player) {
        return null;
    }

    @Override
    public void saveMail(Mail mail) {

    }

    @Override
    public boolean deleteMail(Mail mail) {
        return false;
    }

    @Override
    public List<GameMainQuest> getAllQuests(Player player) {
        return null;
    }

    @Override
    public void saveQuest(GameMainQuest quest) {

    }

    @Override
    public boolean deleteQuest(GameMainQuest quest) {
        return false;
    }

    @Override
    public GameHome getHomeByUid(int id) {
        return null;
    }

    @Override
    public void saveHome(GameHome gameHome) {

    }

    @Override
    public BattlePassManager loadBattlePass(Player player) {
        return null;
    }

    @Override
    public void saveBattlePass(BattlePassManager manager) {

    }

    @Override
    public PlayerActivityData getPlayerActivityData(int uid, int activityId) {
        return null;
    }

    @Override
    public void savePlayerActivityData(PlayerActivityData playerActivityData) {

    }

    @Override
    public MusicGameBeatmap getMusicGameBeatmap(long musicShareId) {
        return null;
    }

    @Override
    public void saveMusicGameBeatmap(MusicGameBeatmap musicGameBeatmap) {

    }
}
