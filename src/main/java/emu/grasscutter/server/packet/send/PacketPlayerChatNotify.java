package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PlayerChatNotifyOuterClass.PlayerChatNotify;

public class PacketPlayerChatNotify extends BasePacket {

    public PacketPlayerChatNotify(Player sender, int channelId, String message) {
        super(PacketOpcodes.PlayerChatNotify);

        ChatInfo info =
                ChatInfo.newBuilder()
                        .setTime((int) (System.currentTimeMillis() / 1000))
                        .setUid(sender.getUid())
                        .setText(message)
                        .build();

        PlayerChatNotify proto =
                PlayerChatNotify.newBuilder().setChannelId(channelId).setChatInfo(info).build();

        this.setData(proto);
    }

    public PacketPlayerChatNotify(Player sender, int channelId, int emote) {
        super(PacketOpcodes.PlayerChatNotify);

        ChatInfo info =
                ChatInfo.newBuilder()
                        .setTime((int) (System.currentTimeMillis() / 1000))
                        .setUid(sender.getUid())
                        .setIcon(emote)
                        .build();

        PlayerChatNotify proto =
                PlayerChatNotify.newBuilder().setChannelId(channelId).setChatInfo(info).build();

        this.setData(proto);
    }

    public PacketPlayerChatNotify(Player sender, int channelId, ChatInfo.SystemHint systemHint) {
        super(PacketOpcodes.PlayerChatNotify);

        ChatInfo info =
                ChatInfo.newBuilder()
                        .setTime((int) (System.currentTimeMillis() / 1000))
                        .setUid(sender.getUid())
                        .setSystemHint(systemHint)
                        .build();

        PlayerChatNotify proto =
                PlayerChatNotify.newBuilder().setChannelId(channelId).setChatInfo(info).build();

        this.setData(proto);
    }
}
