package emu.grasscutter.database;

import java.util.List;

import com.mongodb.client.result.DeleteResult;

import dev.morphia.query.FindOptions;
import dev.morphia.query.Sort;
import dev.morphia.query.experimental.filters.Filters;
import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
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

import static com.mongodb.client.model.Filters.eq;

public final class DatabaseHelper {
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

		// Account
		Account account = new Account();
		account.setUsername(username);
		account.setId(Integer.toString(DatabaseManager.getNextId(account)));

		if (reservedUid > 0) {
			account.setReservedPlayerUid(reservedUid);
		}

		DatabaseHelper.saveAccount(account);
		return account;
	}

	@Deprecated
	public static Account createAccountWithPassword(String username, String password) {
		// Unique names only
		Account exists = DatabaseHelper.getAccountByName(username);
		if (exists != null) {
			return null;
		}

		// Account
		Account account = new Account();
		account.setId(Integer.toString(DatabaseManager.getNextId(account)));
		account.setUsername(username);
		account.setPassword(password);
		DatabaseHelper.saveAccount(account);
		return account;
	}

	public static void saveAccount(Account account) {
		DatabaseManager.getAccountDatastore().save(account);
	}

	public static Account getAccountByName(String username) {
		return DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("username", username)).first();
	}

	public static Account getAccountByToken(String token) {
		if(token == null) return null;
		return DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("token", token)).first();
	}

	public static Account getAccountBySessionKey(String sessionKey) {
		if(sessionKey == null) return null;
		return DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("sessionKey", sessionKey)).first();
	}

	public static Account getAccountById(String uid) {
		return DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("_id", uid)).first();
	}

	public static Account getAccountByPlayerId(int playerId) {
		return DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("reservedPlayerId", playerId)).first();
	}

	public static boolean checkIfAccountExists(String name) {
		return DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("username", name)).count() > 0;
	}

	public static boolean checkIfAccountExists(int reservedUid) {
		return DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("reservedPlayerId", reservedUid)).count() > 0;
	}

	public static void deleteAccount(Account target) {
		// To delete an account, we need to also delete all the other documents in the database that reference the account.
		// This should optimally be wrapped inside a transaction, to make sure an error thrown mid-way does not leave the
		// database in an inconsistent state, but unfortunately Mongo only supports that when we have a replica set ...

		Player player = Grasscutter.getGameServer().getPlayerByAccountId(target.getId());

        if (player != null) {
        	// Close session first
            player.getSession().close();

            // Delete data from collections
            DatabaseManager.getGameDatabase().getCollection("activities").deleteMany(eq("uid",player.getUid()));
            DatabaseManager.getGameDatabase().getCollection("homes").deleteMany(eq("ownerUid",player.getUid()));
    		DatabaseManager.getGameDatabase().getCollection("mail").deleteMany(eq("ownerUid", player.getUid()));
    		DatabaseManager.getGameDatabase().getCollection("avatars").deleteMany(eq("ownerId", player.getUid()));
    		DatabaseManager.getGameDatabase().getCollection("gachas").deleteMany(eq("ownerId", player.getUid()));
    		DatabaseManager.getGameDatabase().getCollection("items").deleteMany(eq("ownerId", player.getUid()));
    		DatabaseManager.getGameDatabase().getCollection("quests").deleteMany(eq("ownerUid", player.getUid()));
    		DatabaseManager.getGameDatabase().getCollection("battlepass").deleteMany(eq("ownerUid", player.getUid()));

    		// Delete friendships.
    		// Here, we need to make sure to not only delete the deleted account's friendships,
    		// but also all friendship entries for that account's friends.
    		DatabaseManager.getGameDatabase().getCollection("friendships").deleteMany(eq("ownerId", player.getUid()));
    		DatabaseManager.getGameDatabase().getCollection("friendships").deleteMany(eq("friendId", player.getUid()));

    		// Delete the player last.
    		DatabaseManager.getGameDatastore().find(Player.class).filter(Filters.eq("id", player.getUid())).delete();
        }

		// Finally, delete the account itself.
		DatabaseManager.getAccountDatastore().find(Account.class).filter(Filters.eq("id", target.getId())).delete();
	}

	public static List<Player> getAllPlayers() {
		return DatabaseManager.getGameDatastore().find(Player.class).stream().toList();
	}

	public static Player getPlayerByUid(int id) {
		return DatabaseManager.getGameDatastore().find(Player.class).filter(Filters.eq("_id", id)).first();
	}

    @Deprecated
	public static Player getPlayerByAccount(Account account) {
		return DatabaseManager.getGameDatastore().find(Player.class).filter(Filters.eq("accountId", account.getId())).first();
	}

    public static Player getPlayerByAccount(Account account, Class<? extends Player> playerClass) {
        return DatabaseManager.getGameDatastore().find(playerClass).filter(Filters.eq("accountId", account.getId())).first();
    }

	public static boolean checkIfPlayerExists(int uid) {
		return DatabaseManager.getGameDatastore().find(Player.class).filter(Filters.eq("_id", uid)).count() > 0;
	}

	public static synchronized Player generatePlayerUid(Player character, int reservedId) {
		// Check if reserved id
		int id;
		if (reservedId > 0 && !checkIfPlayerExists(reservedId)) {
			id = reservedId;
			character.setUid(id);
		} else {
			do {
				id = DatabaseManager.getNextId(character);
			}
			while (checkIfPlayerExists(id));
			character.setUid(id);
		}
		// Save to database
		DatabaseManager.getGameDatastore().save(character);
		return character;
	}

	public static synchronized int getNextPlayerId(int reservedId) {
		// Check if reserved id
		int id;
		if (reservedId > 0 && !checkIfPlayerExists(reservedId)) {
			id = reservedId;
		} else {
			do {
				id = DatabaseManager.getNextId(Player.class);
			}
			while (checkIfPlayerExists(id));
		}
		return id;
	}

	public static void savePlayer(Player character) {
		DatabaseManager.getGameDatastore().save(character);
	}

	public static void saveAvatar(Avatar avatar) {
		DatabaseManager.getGameDatastore().save(avatar);
	}

	public static List<Avatar> getAvatars(Player player) {
		return DatabaseManager.getGameDatastore().find(Avatar.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}

	public static void saveItem(GameItem item) {
		DatabaseManager.getGameDatastore().save(item);
	}

	public static boolean deleteItem(GameItem item) {
		DeleteResult result = DatabaseManager.getGameDatastore().delete(item);
		return result.wasAcknowledged();
	}

	public static List<GameItem> getInventoryItems(Player player) {
		return DatabaseManager.getGameDatastore().find(GameItem.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}

	public static List<Friendship> getFriends(Player player) {
		return DatabaseManager.getGameDatastore().find(Friendship.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}

	public static List<Friendship> getReverseFriends(Player player) {
		return DatabaseManager.getGameDatastore().find(Friendship.class).filter(Filters.eq("friendId", player.getUid())).stream().toList();
	}

	public static void saveFriendship(Friendship friendship) {
		DatabaseManager.getGameDatastore().save(friendship);
	}

	public static void deleteFriendship(Friendship friendship) {
		DatabaseManager.getGameDatastore().delete(friendship);
	}

	public static Friendship getReverseFriendship(Friendship friendship) {
		return DatabaseManager.getGameDatastore().find(Friendship.class).filter(Filters.and(
				Filters.eq("ownerId", friendship.getFriendId()),
				Filters.eq("friendId", friendship.getOwnerId())
		)).first();
	}

	public static List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType){
		return getGachaRecords(ownerId, page, gachaType, 10);
	}

	public static List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType, int pageSize){
		return DatabaseManager.getGameDatastore().find(GachaRecord.class).filter(
			Filters.eq("ownerId", ownerId),
			Filters.eq("gachaType", gachaType)
		).iterator(new FindOptions()
				.sort(Sort.descending("transactionDate"))
				.skip(pageSize * page)
				.limit(pageSize)
		).toList();
	}

	public static long getGachaRecordsMaxPage(int ownerId, int page, int gachaType){
		return getGachaRecordsMaxPage(ownerId, page, gachaType, 10);
	}

	public static long getGachaRecordsMaxPage(int ownerId, int page, int gachaType, int pageSize){
		long count = DatabaseManager.getGameDatastore().find(GachaRecord.class).filter(
			Filters.eq("ownerId", ownerId),
			Filters.eq("gachaType", gachaType)
		).count();
		return count / 10 + (count % 10 > 0 ? 1 : 0 );
	}

	public static void saveGachaRecord(GachaRecord gachaRecord){
		DatabaseManager.getGameDatastore().save(gachaRecord);
	}

	public static List<Mail> getAllMail(Player player) {
		return DatabaseManager.getGameDatastore().find(Mail.class).filter(Filters.eq("ownerUid", player.getUid())).stream().toList();
	}

	public static void saveMail(Mail mail) {
		DatabaseManager.getGameDatastore().save(mail);
	}

	public static boolean deleteMail(Mail mail) {
		DeleteResult result = DatabaseManager.getGameDatastore().delete(mail);
		return result.wasAcknowledged();
	}

	public static List<GameMainQuest> getAllQuests(Player player) {
		return DatabaseManager.getGameDatastore().find(GameMainQuest.class).filter(Filters.eq("ownerUid", player.getUid())).stream().toList();
	}

	public static void saveQuest(GameMainQuest quest) {
		DatabaseManager.getGameDatastore().save(quest);
	}

	public static boolean deleteQuest(GameMainQuest quest) {
		return DatabaseManager.getGameDatastore().delete(quest).wasAcknowledged();
	}

	public static GameHome getHomeByUid(int id) {
		return DatabaseManager.getGameDatastore().find(GameHome.class).filter(Filters.eq("ownerUid", id)).first();
	}

	public static void saveHome(GameHome gameHome) {
		DatabaseManager.getGameDatastore().save(gameHome);
	}

	public static BattlePassManager loadBattlePass(Player player) {
		BattlePassManager manager = DatabaseManager.getGameDatastore().find(BattlePassManager.class).filter(Filters.eq("ownerUid", player.getUid())).first();
		if (manager == null) {
			manager = new BattlePassManager(player);
			manager.save();
		} else {
			manager.setPlayer(player);
		}
		return manager;
	}

	public static void saveBattlePass(BattlePassManager manager) {
		DatabaseManager.getGameDatastore().save(manager);
	}

    public static PlayerActivityData getPlayerActivityData(int uid, int activityId) {
        return DatabaseManager.getGameDatastore().find(PlayerActivityData.class)
            .filter(Filters.and(Filters.eq("uid", uid),Filters.eq("activityId", activityId)))
            .first();
    }

    public static void savePlayerActivityData(PlayerActivityData playerActivityData) {
        DatabaseManager.getGameDatastore().save(playerActivityData);
    }
    public static MusicGameBeatmap getMusicGameBeatmap(long musicShareId) {
        return DatabaseManager.getGameDatastore().find(MusicGameBeatmap.class)
            .filter(Filters.eq("musicShareId", musicShareId))
            .first();
    }

    public static void saveMusicGameBeatmap(MusicGameBeatmap musicGameBeatmap) {
        DatabaseManager.getGameDatastore().save(musicGameBeatmap);
    }
}
