package emu.grasscutter.game.world.data;

import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.EnterTypeOuterClass;
import emu.grasscutter.server.event.player.PlayerTeleportEvent;
import lombok.*;

@Data
@Builder
public final class TeleportProperties {
    private final int sceneId;
    private final int dungeonId;
    private final PlayerTeleportEvent.TeleportType teleportType;
    private final EnterReason enterReason;
    private Position teleportTo;
    private Position teleportRot;
    private EnterTypeOuterClass.EnterType enterType;
}
