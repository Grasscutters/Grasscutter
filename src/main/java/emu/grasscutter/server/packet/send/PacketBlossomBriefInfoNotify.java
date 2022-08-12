package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.game.managers.blossom.BlossomType;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BlossomBriefInfoNotifyOuterClass;
import emu.grasscutter.net.proto.BlossomBriefInfoOuterClass;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class PacketBlossomBriefInfoNotify extends BasePacket {
    public PacketBlossomBriefInfoNotify(Int2ObjectMap<List<SpawnDataEntry>> bloomsPerScene) {
        super(PacketOpcodes.BlossomBriefInfoNotify);
        var proto = BlossomBriefInfoNotifyOuterClass.BlossomBriefInfoNotify.newBuilder();

        bloomsPerScene.forEach((sceneId, gadgets) -> {
            gadgets.forEach(gadget -> {
                BlossomType type = BlossomType.valueOf(gadget.getGadgetId());
                if (type == null) return;

                proto.addBriefInfoList(
                    BlossomBriefInfoOuterClass.BlossomBriefInfo.newBuilder()
                        .setSceneId(sceneId)
                        .setPos(gadget.getPos().toProto())
                        .setResin(20)
                        .setMonsterLevel(30)
                        .setRewardId(type.getRewardId())
                        .setCircleCampId(type.getCircleCampId())
                        .setRefreshId(type.getRefreshId())
                );
            });
        });
        this.setData(proto);
    }
}