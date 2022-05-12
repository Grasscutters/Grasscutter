package emu.grasscutter.server.dispatch.json;

public class LoginAccountRequestJson {
	public String account;
	public String password;
	public boolean is_crypto;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIs_crypto() {
		return is_crypto;
	}

	public void setIs_crypto(boolean is_crypto) {
		this.is_crypto = is_crypto;
	}
}
