package emu.grasscutter.game;

import dev.morphia.annotations.AlsoLoad;
import dev.morphia.annotations.Collation;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import dev.morphia.annotations.PreLoad;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;
import dev.morphia.annotations.IndexOptions;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

@Entity(value = "accounts", noClassnameStored = true)
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
		return email;
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
		return this.permissions.contains(permission) || this.permissions.contains("*") ? true : false;
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
	
	@PreLoad
	public void onLoad(DBObject dbObj) {
		// Grant the superuser permissions to accounts created before the permissions update
		if (!dbObj.containsField("permissions")) {
			this.addPermission("*");
		}
	}
	
	public void save() {
		DatabaseHelper.saveAccount(this);
	}
}
