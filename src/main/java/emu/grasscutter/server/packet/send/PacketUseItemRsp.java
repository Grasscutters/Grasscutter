package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.UseItemRspOuterClass.UseItemRsp;

public class PacketUseItemRsp extends BasePacket {

    public PacketUseItemRsp(long targetGuid, GameItem useItem) {
        super(PacketOpcodes.UseItemRsp);

        UseItemRsp proto =
                UseItemRsp.newBuilder()
                        .setTargetGuid(targetGuid)
                        .setItemId(useItem.getItemId())
                        .setGuid(useItem.getGuid())
                        .build();

        this.setData(proto);
    }

    public PacketUseItemRsp() {
        super(PacketOpcodes.UseItemRsp);

        UseItemRsp proto =
                UseItemRsp.newBuilder().setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE).build();

        this.setData(proto);
    }
}
