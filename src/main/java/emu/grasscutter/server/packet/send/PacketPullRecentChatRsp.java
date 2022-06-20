package emu.grasscutter.server.packet.send;

import emu.grasscutter.GameConstants;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PullRecentChatRspOuterClass.PullRecentChatRsp;
import emu.grasscutter.utils.Utils;

import static emu.grasscutter.Configuration.GAME_INFO;

public class PacketPullRecentChatRsp extends BasePacket {
    public PacketPullRecentChatRsp(Player player) {
        super(PacketOpcodes.PullRecentChatRsp);

        var joinOptions = GAME_INFO.joinOptions;
        PullRecentChatRsp.Builder proto = PullRecentChatRsp.newBuilder();

        if (joinOptions.welcomeEmotes != null && joinOptions.welcomeEmotes.length > 0) {
            ChatInfo welcomeEmote = ChatInfo.newBuilder()
                .setTime((int) (System.currentTimeMillis() / 1000))
                .setUid(GameConstants.SERVER_CONSOLE_UID)
                .setToUid(player.getUid())
                .setIcon(joinOptions.welcomeEmotes[Utils.randomRange(0, joinOptions.welcomeEmotes.length - 1)])
                .build();

            proto.addChatInfo(welcomeEmote);
        }

        if (joinOptions.welcomeMessage != null && joinOptions.welcomeMessage.length() > 0) {
            ChatInfo welcomeMessage = ChatInfo.newBuilder()
                .setTime((int) (System.currentTimeMillis() / 1000))
                .setUid(GameConstants.SERVER_CONSOLE_UID)
                .setToUid(player.getUid())
                .setText(joinOptions.welcomeMessage)
                .build();
            proto.addChatInfo(welcomeMessage);
        }

        this.setData(proto);
    }
}
