package emu.grasscutter.game;

import emu.grasscutter.game.player.Player;

public class CoopRequest {
	private final Player requester;
	private final long requestTime;
	private final long expireTime;
	
	public CoopRequest(Player requester) {
		this.requester = requester;
		this.requestTime = System.currentTimeMillis();
		this.expireTime = this.requestTime + 10000;
	}

	public Player getRequester() {
		return requester;
	}

	public long getRequestTime() {
		return requestTime;
	}

	public long getExpireTime() {
		return expireTime;
	}
	
	public boolean isExpired() {
		return System.currentTimeMillis() > getExpireTime();
	}
}
