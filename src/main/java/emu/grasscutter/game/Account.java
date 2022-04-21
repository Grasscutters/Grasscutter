package emu.grasscutter.game;

import dev.morphia.annotations.*;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "accounts", useDiscriminator = false)
public class Account {
	@Id private String id;
	
	@Indexed(options = @IndexOptions(unique = true))
	@Collation(locale = "simple", caseLevel = true)
	private String username;
	private String password; // Unused for now
	
	private int playerId;
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

	public int getPlayerId() {
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
	
	public void save() {
		DatabaseHelper.saveAccount(this);
	}
	
//  TODO: Find an implementation for this. Morphia 2.0 breaks this method for some reason?
//	@PreLoad
//	public void onLoad(DBObject object) {
//		// Grant the superuser permissions to accounts created before the permissions update
//		if (!object.containsField("permissions")) {
//			this.addPermission("*");
//		}
//	}
}
