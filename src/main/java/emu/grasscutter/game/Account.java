package emu.grasscutter.game;

import dev.morphia.annotations.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.DBObject;

@Entity(value = "accounts", useDiscriminator = false)
public class Account {
	@Id private String id;
	
	@Indexed(options = @IndexOptions(unique = true))
	@Collation(locale = "simple", caseLevel = true)
	private String username;
	private String password; // Unused for now
	
	@AlsoLoad("playerUid") private int playerId;
	private String email;
	
	private String token;
	private String sessionKey; // Session token for dispatch server
	private List<String> permissions;
	
	@Deprecated
	public Account() {
		this.permissions = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getPlayerUid() {
		return this.playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public String getEmail() {
		if(email != null && !email.isEmpty()) {
			return email;
		} else {
			return "";
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public String generateSessionKey() {
		this.sessionKey = Utils.bytesToHex(Crypto.createSessionKey(32));
		this.save();
		return this.sessionKey;
	}

	/**
	 * The collection of a player's permissions.
	 */
	public List<String> getPermissions() {
		return this.permissions;
	}
	
	public boolean addPermission(String permission) {
		if(this.permissions.contains(permission)) return false;
		this.permissions.add(permission); return true;
	}

	public boolean hasPermission(String permission) {
		return this.permissions.contains(permission) ||
                this.permissions.contains("*") ||
                (this.permissions.contains("player") || this.permissions.contains("player.*")) && permission.startsWith("player.") ||
                (this.permissions.contains("server") || this.permissions.contains("server.*")) && permission.startsWith("server.");
	}
	
	public boolean removePermission(String permission) {
		return this.permissions.remove(permission);
	}
	
	// TODO make unique
	public String generateLoginToken() {
		this.token = Utils.bytesToHex(Crypto.createSessionKey(32));
		this.save();
		return this.token;
	}
	
	public void save() {
		DatabaseHelper.saveAccount(this);
	}

	@PreLoad
	public void onLoad(Document document) {
		// Grant the superuser permissions to accounts created before the permissions update
		if (!document.containsKey("permissions")) {
			this.addPermission("*");
		}
	}
}
