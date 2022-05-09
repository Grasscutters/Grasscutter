package emu.grasscutter.server.packet.send;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetOnlinePlayerListReqOuterClass;
import emu.grasscutter.net.proto.GetOnlinePlayerListRspOuterClass.*;
import emu.grasscutter.net.proto.MpSettingTypeOuterClass;
import emu.grasscutter.net.proto.OnlinePlayerInfoOuterClass.OnlinePlayerInfo;
import emu.grasscutter.net.proto.ProfilePictureOuterClass.ProfilePicture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PacketGetOnlinePlayerListRsp extends BasePacket {
    public PacketGetOnlinePlayerListRsp(Player session){
        super(PacketOpcodes.GetOnlinePlayerListRsp);
        
        List<Player> players = Grasscutter.getGameServer().getPlayers().values().stream().limit(50).toList();
        
        GetOnlinePlayerListRsp.Builder proto = GetOnlinePlayerListRsp.newBuilder();
        
        if (players.size() != 0) {
            for(Player player : players) {
                if (player.getUid() == session.getUid()) continue;
                
                proto.addPlayerInfoList(player.getOnlinePlayerInfo());
            }
        }
        
        this.setData(proto);
    }
}
