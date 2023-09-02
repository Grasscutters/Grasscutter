package emu.grasscutter.server.event.player;

import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GachaItemOuterClass.GachaItem;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import java.util.List;
import lombok.*;

@Getter
public final class PlayerWishEvent extends PlayerEvent implements Cancellable {
    @Setter private GachaBanner banner;
    @Setter private int wishCount;
    private final Pity pity;
    @Setter private List<GameItem> receivedItems;

    private boolean preItems = true;

    public PlayerWishEvent(Player player, GachaBanner banner, int wishCount, Pity pity) {
        super(player);

        this.banner = banner;
        this.wishCount = wishCount;
        this.pity = pity;
    }

    /**
     * Should be invoked after items have been generated. This will recall the event.
     *
     * @param receivedItems The items received.
     */
    public void finish(List<GameItem> receivedItems) {
        this.receivedItems = receivedItems;
        this.preItems = false;

        this.call();
    }

    @Getter
    @AllArgsConstructor
    public static final class Pity {
        private final int five;
        private final int four;
        private final boolean guaranteed4;
        private final boolean guaranteed5;
    }

    @Getter
    @AllArgsConstructor
    public static final class WishCompute {
        private final GameItem item;
        private final GachaItem.Builder gacha;
        private final int addStardust;
        private final int addStarglitter;
        private final boolean isTransferItem;
    }
}
