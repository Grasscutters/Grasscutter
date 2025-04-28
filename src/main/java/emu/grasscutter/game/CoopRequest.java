package emu.grasscutter.game;

import emu.grasscutter.game.player.Player;
import lombok.Getter;

@Getter
public class CoopRequest {
    private final Player requester;
    private final long requestTime;
    private final long expireTime;

    public CoopRequest(Player requester) {
        this.requester = requester;
        this.requestTime = System.currentTimeMillis();
        this.expireTime = this.requestTime + 10000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > getExpireTime();
    }
}
