package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ClientScriptEventNotifyOuterClass.ClientScriptEventNotify;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.game.GameSession;
import lombok.val;

@Opcodes(PacketOpcodes.ClientScriptEventNotify)
public class HandlerClientScriptEventNotify extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        val data = ClientScriptEventNotify.parseFrom(payload);
        val scriptManager = session.getPlayer().getScene().getScriptManager();
        val args =
                new ScriptArgs(0, data.getEventType())
                        .setSourceEntityId(data.getSourceEntityId())
                        .setTargetEntityId(data.getTargetEntityId());

        for (int i = 0; i < data.getParamListCount(); i++) {
            switch (i) {
                case 0 -> args.setParam1(data.getParamList(i));
                case 1 -> args.setParam2(data.getParamList(i));
                case 2 -> args.setParam3(data.getParamList(i));
            }
        }

        if (data.getEventType() == EventType.EVENT_AVATAR_NEAR_PLATFORM) {
            if (data.getParamList(0) == 0) {
                Grasscutter.getLogger()
                        .debug(
                                "Found a zero Param1 for an EVENT_AVATAR_NEAR_PLATFORM. Doing the configID workaround.");
                val entity = session.getPlayer().getScene().getEntityById(data.getSourceEntityId());
                if (entity == null) {
                    Grasscutter.getLogger().debug("But it failed.");
                } else {
                    args.setParam1(entity.getConfigId());
                }
            }
        }

        scriptManager.callEvent(args);
    }
}
