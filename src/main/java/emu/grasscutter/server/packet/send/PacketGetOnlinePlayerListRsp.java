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
        Map<Integer, Player> playersMap = Grasscutter.getGameServer().getPlayers();
        GetOnlinePlayerListRsp.Builder proto = GetOnlinePlayerListRsp.newBuilder();
        if(playersMap.size() != 0){
            List<OnlinePlayerInfo> playerInfoList = new ArrayList<>();

            for(Player player:playersMap.values()){
                ProfilePicture.Builder picture = ProfilePicture.newBuilder();
                OnlinePlayerInfo.Builder playerInfo = OnlinePlayerInfo.newBuilder();

                if(player.getUid() == session.getUid())continue;
                picture.setAvatarId(player.getProfile().getAvatarId())
                        .build();
                System.out.println(player.getHeadImage());
                playerInfo.setUid(player.getUid())
                        .setNickname(player.getNickname())
                        .setPlayerLevel(player.getLevel())
                        .setMpSettingType(MpSettingTypeOuterClass.MpSettingType.MP_SETTING_ENTER_AFTER_APPLY)
                        .setCurPlayerNumInWorld(player.getWorld().getPlayerCount())
                        .setWorldLevel(player.getWorldLevel())
                        .setNameCardId(player.getNameCardId())
                        .setProfilePicture(picture);
                if(!Objects.equals(player.getSignature(), "")){
                    playerInfo.setSignature(player.getSignature());
                }
                playerInfoList.add(playerInfo.build());
            }
            for (OnlinePlayerInfo onlinePlayerInfo : playerInfoList) {
                proto.addPlayerInfoList(onlinePlayerInfo).build();
            }
        }
        this.setData(proto);
    }
}
