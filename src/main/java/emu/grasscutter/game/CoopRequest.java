package emu.grasscutter.game;

public class CoopRequest {
	private final GenshinPlayer requester;
	private final long requestTime;
	private final long expireTime;
	
	public CoopRequest(GenshinPlayer requester) {
		this.requester = requester;
		this.requestTime = System.currentTimeMillis();
		this.expireTime = this.requestTime + 10000;
	}

	public GenshinPlayer getRequester() {
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
