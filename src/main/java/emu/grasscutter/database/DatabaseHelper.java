package emu.grasscutter.database;

import java.util.List;

import com.mongodb.client.result.DeleteResult;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Sort;
import dev.morphia.query.experimental.filters.Filters;
import emu.grasscutter.GameConstants;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.gacha.GachaRecord;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;

public final class DatabaseHelper {
	public static Account createAccount(String username) {
		return createAccountWithId(username, 0);
	}

	public static Account createAccountWithId(String username, int reservedId) {
		// Unique names only
		Account exists = DatabaseHelper.getAccountByName(username);
		if (exists != null) {
			return null;
		}

		// Make sure there are no id collisions
		if (reservedId > 0) {
			// Cannot make account with the same uid as the server console
			if (reservedId == GameConstants.SERVER_CONSOLE_UID) {
				return null;
			}
			exists = DatabaseHelper.getAccountByPlayerId(reservedId);
			if (exists != null) {
				return null;
			}
		}

		// Account
		Account account = new Account();
		account.setUsername(username);
		account.setId(Integer.toString(DatabaseManager.getNextId(account)));

		if (reservedId > 0) {
			account.setPlayerId(reservedId);
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
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("username", username)).first();
	}

	public static Account getAccountByToken(String token) {
		if(token == null) return null;
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("token", token)).first();
	}

	public static Account getAccountBySessionKey(String sessionKey) {
		if(sessionKey == null) return null;
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("sessionKey", sessionKey)).first();
	}

	public static Account getAccountById(String uid) {
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("_id", uid)).first();
	}

	public static Account getAccountByPlayerId(int playerId) {
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("playerId", playerId)).first();
	}

	public static boolean deleteAccount(String username) {
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("username", username)).delete().getDeletedCount() > 0;
	}

	public static List<Player> getAllPlayers() {
		return DatabaseManager.getDatastore().find(Player.class).stream().toList();
	}

	public static Player getPlayerById(int id) {
		return DatabaseManager.getDatastore().find(Player.class).filter(Filters.eq("_id", id)).first();
	}

	public static boolean checkPlayerExists(int id) {
		return DatabaseManager.getDatastore().find(Player.class).filter(Filters.eq("_id", id)).first() != null;
	}

	public static synchronized Player createPlayer(Player character, int reservedId) {
		// Check if reserved id
		int id;
		if (reservedId > 0 && !checkPlayerExists(reservedId)) {
			id = reservedId;
			character.setUid(id);
		} else {
			do {
				id = DatabaseManager.getNextId(character);
			}
			while (checkPlayerExists(id));
			character.setUid(id);
		}
		// Save to database
		DatabaseManager.getDatastore().save(character);
		return character;
	}

	public static synchronized int getNextPlayerId(int reservedId) {
		// Check if reserved id
		int id;
		if (reservedId > 0 && !checkPlayerExists(reservedId)) {
			id = reservedId;
		} else {
			do {
				id = DatabaseManager.getNextId(Player.class);
			}
			while (checkPlayerExists(id));
		}
		return id;
	}

	public static void savePlayer(Player character) {
		DatabaseManager.getDatastore().save(character);
	}

	public static void saveAvatar(Avatar avatar) {
		DatabaseManager.getDatastore().save(avatar);
	}

	public static List<Avatar> getAvatars(Player player) {
		return DatabaseManager.getDatastore().find(Avatar.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}

	public static void saveItem(GameItem item) {
		DatabaseManager.getDatastore().save(item);
	}

	public static boolean deleteItem(GameItem item) {
		DeleteResult result = DatabaseManager.getDatastore().delete(item);
		return result.wasAcknowledged();
	}

	public static List<GameItem> getInventoryItems(Player player) {
		return DatabaseManager.getDatastore().find(GameItem.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}
	
	public static List<Friendship> getFriends(Player player) {
		return DatabaseManager.getDatastore().find(Friendship.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}

	public static List<Friendship> getReverseFriends(Player player) {
		return DatabaseManager.getDatastore().find(Friendship.class).filter(Filters.eq("friendId", player.getUid())).stream().toList();
	}

	public static void saveFriendship(Friendship friendship) {
		DatabaseManager.getDatastore().save(friendship);
	}

	public static void deleteFriendship(Friendship friendship) {
		DatabaseManager.getDatastore().delete(friendship);
	}

	public static Friendship getReverseFriendship(Friendship friendship) {
		return DatabaseManager.getDatastore().find(Friendship.class).filter(Filters.and(
				Filters.eq("ownerId", friendship.getFriendId()),
				Filters.eq("friendId", friendship.getOwnerId())
		)).first();
	}

	public static List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType){
		return getGachaRecords(ownerId, page, gachaType, 10);
	}

	public static List<GachaRecord> getGachaRecords(int ownerId, int page, int gachaType, int pageSize){
		return DatabaseManager.getDatastore().find(GachaRecord.class).filter(
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
		long count = DatabaseManager.getDatastore().find(GachaRecord.class).filter(
			Filters.eq("ownerId", ownerId),
			Filters.eq("gachaType", gachaType)
		).count();
		return count / 10 + (count % 10 > 0 ? 1 : 0 );
	}

	public static void saveGachaRecord(GachaRecord gachaRecord){
		DatabaseManager.getDatastore().save(gachaRecord);
	}
	
	public static List<Mail> getAllMail(Player player) {
		return DatabaseManager.getDatastore().find(Mail.class).filter(Filters.eq("ownerUid", player.getUid())).stream().toList();
	}
	
	public static void saveMail(Mail mail) {
		DatabaseManager.getDatastore().save(mail);
	}
	
	public static boolean deleteMail(Mail mail) {
		DeleteResult result = DatabaseManager.getDatastore().delete(mail);
		return result.wasAcknowledged();
	}
}
