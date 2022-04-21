package emu.grasscutter.database;

import java.util.List;

import com.mongodb.WriteResult;

import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;
import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.inventory.GenshinItem;

public class DatabaseHelper {
	
	protected static FindOptions FIND_ONE = new FindOptions().limit(1);
	
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
			if (reservedId == GenshinConstants.SERVER_CONSOLE_UID) {
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
		MorphiaCursor<Account> cursor = DatabaseManager.getAccountDatastore().createQuery(Account.class).field("username").equalIgnoreCase(username).find(FIND_ONE);
		if (!cursor.hasNext()) return null;
		return cursor.next();
	}
	
	public static Account getAccountByToken(String token) {
		if (token == null) return null;
		MorphiaCursor<Account> cursor = DatabaseManager.getAccountDatastore().createQuery(Account.class).field("token").equal(token).find(FIND_ONE);
		if (!cursor.hasNext()) return null;
		return cursor.next();
	}
	
	public static Account getAccountById(String uid) {
		MorphiaCursor<Account> cursor = DatabaseManager.getAccountDatastore().createQuery(Account.class).field("_id").equal(uid).find(FIND_ONE);
		if (!cursor.hasNext()) return null;
		return cursor.next();
	}
	
	public static Account getAccountByPlayerId(int playerId) {
		MorphiaCursor<Account> cursor = DatabaseManager.getAccountDatastore().createQuery(Account.class).field("playerId").equal(playerId).find(FIND_ONE);
		if (!cursor.hasNext()) return null;
		return cursor.next();
	}
	
	public static boolean deleteAccount(String username) {
		Query<Account> q = DatabaseManager.getAccountDatastore().createQuery(Account.class).field("username").equalIgnoreCase(username);
		return DatabaseManager.getDatastore().findAndDelete(q) != null;
	}
	
	public static GenshinPlayer getPlayerById(int id) {
		Query<GenshinPlayer> query = DatabaseManager.getDatastore().createQuery(GenshinPlayer.class).field("_id").equal(id);
		MorphiaCursor<GenshinPlayer> cursor = query.find(FIND_ONE);
		if (!cursor.hasNext()) return null;
		return cursor.next();
	}
	
	public static boolean checkPlayerExists(int id) {
		MorphiaCursor<GenshinPlayer> query = DatabaseManager.getDatastore().createQuery(GenshinPlayer.class).field("_id").equal(id).find(FIND_ONE);
		return query.hasNext();
	}
	
	public static synchronized GenshinPlayer createPlayer(GenshinPlayer character, int reservedId) {
		// Check if reserved id
		int id = 0;
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
		int id = 0;
		if (reservedId > 0 && !checkPlayerExists(reservedId)) {
			id = reservedId;
		} else {
			do {
				id = DatabaseManager.getNextId(GenshinPlayer.class);
			}
			while (checkPlayerExists(id));
		}
		return id;
	}
	
	public static void savePlayer(GenshinPlayer character) {
		DatabaseManager.getDatastore().save(character);
	}
	
	public static void saveAvatar(GenshinAvatar avatar) {
		DatabaseManager.getDatastore().save(avatar);
	}
	
	public static List<GenshinAvatar> getAvatars(GenshinPlayer player) {
		Query<GenshinAvatar> query = DatabaseManager.getDatastore().createQuery(GenshinAvatar.class).filter("ownerId", player.getUid());
		return query.find().toList();
	}
	
	public static void saveItem(GenshinItem item) {
		DatabaseManager.getDatastore().save(item);
	}
	
	public static boolean deleteItem(GenshinItem item) {
		WriteResult result = DatabaseManager.getDatastore().delete(item);
		return result.wasAcknowledged();
	}
	
	public static List<GenshinItem> getInventoryItems(GenshinPlayer player) {
		Query<GenshinItem> query = DatabaseManager.getDatastore().createQuery(GenshinItem.class).filter("ownerId", player.getUid());
		return query.find().toList();
	}
	public static List<Friendship> getFriends(GenshinPlayer player) {
		Query<Friendship> query = DatabaseManager.getDatastore().createQuery(Friendship.class).filter("ownerId", player.getUid());
		return query.find().toList();
	}
	
	public static List<Friendship> getReverseFriends(GenshinPlayer player) {
		Query<Friendship> query = DatabaseManager.getDatastore().createQuery(Friendship.class).filter("friendId", player.getUid());
		return query.find().toList();
	}

	public static void saveFriendship(Friendship friendship) {
		DatabaseManager.getDatastore().save(friendship);
	}

	public static void deleteFriendship(Friendship friendship) {
		DatabaseManager.getDatastore().delete(friendship);
	}

	public static Friendship getReverseFriendship(Friendship friendship) {
		Query<Friendship> query = DatabaseManager.getDatastore().createQuery(Friendship.class);
		query.and(
			query.criteria("ownerId").equal(friendship.getFriendId()),
			query.criteria("friendId").equal(friendship.getOwnerId())
		);
		MorphiaCursor<Friendship> reverseFriendship = query.find(FIND_ONE);
		if (!reverseFriendship.hasNext()) return null;
		return reverseFriendship.next();
	}
}
