package emu.grasscutter.server.packet.send;

import java.util.ArrayList;
import java.util.Map;

import emu.grasscutter.game.managers.blossom.BlossomType;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BlossomBriefInfoNotifyOuterClass;
import emu.grasscutter.net.proto.BlossomBriefInfoOuterClass;

public class PacketBlossomBriefInfoNotify extends BasePacket {
    public PacketBlossomBriefInfoNotify(ArrayList<Map.Entry<Integer,SpawnDataEntry>> blooms) {
        super(PacketOpcodes.BlossomBriefInfoNotify);
        var proto
            = BlossomBriefInfoNotifyOuterClass.BlossomBriefInfoNotify.newBuilder();
        for(Map.Entry<Integer,SpawnDataEntry> kv : blooms){
            var gadget = kv.getValue();
            var sceneId = kv.getKey();
            BlossomType type = BlossomType.valueOf(gadget.getGadgetId());
            if(type!=null) {
                var info
                    = BlossomBriefInfoOuterClass.BlossomBriefInfo.newBuilder();
                info.setSceneId(sceneId);
                info.setPos(gadget.getPos().toProto());
                info.setResin(20);
                info.setMonsterLevel(30);
                if(type == BlossomType.GOLDEN_GADGET_ID) {
                    info.setRewardId(4108);
                    info.setCircleCampId(101001001);
                    info.setRefreshId(1);
                }else if(type == BlossomType.BLUE_GADGET_ID) {
                    info.setRewardId(4008);
                    info.setCircleCampId(101002003);
                    info.setRefreshId(2);
                }
                proto.addBriefInfoList(info);
            }
        }
        this.setData(proto);
    }
}