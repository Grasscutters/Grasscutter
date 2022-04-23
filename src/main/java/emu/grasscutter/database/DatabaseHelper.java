package emu.grasscutter.database;

import java.util.List;
import com.mongodb.client.result.DeleteResult;
import de.mkammerer.argon2.Argon2;
import dev.morphia.query.experimental.filters.Filters;
import emu.grasscutter.GenshinConstants;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.utils.Utils;

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

	public static Account getAccountByUsernameAndPassword(String username, String password) {
		Account account = DatabaseHelper.getAccountByName(username);
		if (account == null) {
			return null;
		}

		if (Utils.argon2.verify(account.getPassword(), password.toCharArray())) {
			return account;
		}
		return null;
	}

	public static Account getAccountByOneTimeToken(String token) {
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("oneTimeToken", token)).first();
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

	public static Account getAccountById(String uid) {
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("_id", uid)).first();
	}

	public static Account getAccountByPlayerId(int playerId) {
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("playerId", playerId)).first();
	}

	public static boolean deleteAccount(String username) {
		return DatabaseManager.getDatastore().find(Account.class).filter(Filters.eq("username", username)).delete().getDeletedCount() > 0;
	}

	public static GenshinPlayer getPlayerById(int id) {
		return DatabaseManager.getDatastore().find(GenshinPlayer.class).filter(Filters.eq("_id", id)).first();
	}

	public static boolean checkPlayerExists(int id) {
		return DatabaseManager.getDatastore().find(GenshinPlayer.class).filter(Filters.eq("_id", id)).first() != null;
	}

	public static synchronized GenshinPlayer createPlayer(GenshinPlayer character, int reservedId) {
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
		return DatabaseManager.getDatastore().find(GenshinAvatar.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}

	public static void saveItem(GenshinItem item) {
		DatabaseManager.getDatastore().save(item);
	}

	public static boolean deleteItem(GenshinItem item) {
		DeleteResult result = DatabaseManager.getDatastore().delete(item);
		return result.wasAcknowledged();
	}

	public static List<GenshinItem> getInventoryItems(GenshinPlayer player) {
		return DatabaseManager.getDatastore().find(GenshinItem.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}
	public static List<Friendship> getFriends(GenshinPlayer player) {
		return DatabaseManager.getDatastore().find(Friendship.class).filter(Filters.eq("ownerId", player.getUid())).stream().toList();
	}

	public static List<Friendship> getReverseFriends(GenshinPlayer player) {
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
}
