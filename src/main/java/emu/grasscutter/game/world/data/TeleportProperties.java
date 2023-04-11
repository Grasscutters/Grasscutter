package emu.grasscutter.game.world.data;

import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.net.proto.EnterTypeOuterClass;
import emu.grasscutter.server.event.player.PlayerTeleportEvent;
import emu.grasscutter.utils.Position;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeleportProperties {
    private final int sceneId;
    private final PlayerTeleportEvent.TeleportType teleportType;
    private final EnterReason enterReason;
    private Position teleportTo;
    private Position teleportRot;
    private EnterTypeOuterClass.EnterType enterType;
}
