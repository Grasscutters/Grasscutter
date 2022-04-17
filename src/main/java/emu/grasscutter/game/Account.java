package emu.grasscutter.game;

import dev.morphia.annotations.Collation;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;
import dev.morphia.annotations.IndexOptions;

@Entity(value = "accounts", noClassnameStored = true)
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

	@Deprecated
	public Account() {}

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
	
	// TODO make unique
	public String generateLoginToken() {
		this.token = Utils.bytesToHex(Crypto.createSessionKey(32));
		this.save();
		return this.token;
	}
	
	public void save() {
		DatabaseHelper.saveAccount(this);
	}
}
