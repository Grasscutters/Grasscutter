package emu.grasscutter.game.player;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.ForwardTypeOuterClass.ForwardType;
import java.util.*;

public class InvokeHandler<T> {
    private final List<T> entryListForwardAll;
    private final List<T> entryListForwardAllExceptCur;
    private final List<T> entryListForwardHost;
    private final Class<? extends BasePacket> packetClass;

    public InvokeHandler(Class<? extends BasePacket> packetClass) {
        this.entryListForwardAll = new ArrayList<>();
        this.entryListForwardAllExceptCur = new ArrayList<>();
        this.entryListForwardHost = new ArrayList<>();
        this.packetClass = packetClass;
    }

    public synchronized void addEntry(ForwardType forward, T entry) {
        switch (forward) {
            case FORWARD_TYPE_TO_ALL -> entryListForwardAll.add(entry);
            case FORWARD_TYPE_TO_ALL_EXCEPT_CUR,
                    FORWARD_TYPE_TO_ALL_EXIST_EXCEPT_CUR -> entryListForwardAllExceptCur.add(entry);
            case FORWARD_TYPE_TO_HOST -> entryListForwardHost.add(entry);
            default -> {}
        }
    }

    public synchronized void update(Player player) {
        if (player.getWorld() == null || player.getScene() == null) {
            this.entryListForwardAll.clear();
            this.entryListForwardAllExceptCur.clear();
            this.entryListForwardHost.clear();
            return;
        }

        try {
            if (entryListForwardAll.size() > 0) {
                BasePacket packet =
                        packetClass.getDeclaredConstructor(List.class).newInstance(this.entryListForwardAll);
                player.getScene().broadcastPacket(packet);
                this.entryListForwardAll.clear();
            }
            if (entryListForwardAllExceptCur.size() > 0) {
                BasePacket packet =
                        packetClass
                                .getDeclaredConstructor(List.class)
                                .newInstance(this.entryListForwardAllExceptCur);
                player.getScene().broadcastPacketToOthers(player, packet);
                this.entryListForwardAllExceptCur.clear();
            }
            if (entryListForwardHost.size() > 0) {
                BasePacket packet =
                        packetClass.getDeclaredConstructor(List.class).newInstance(this.entryListForwardHost);
                player.getWorld().getHost().sendPacket(packet);
                this.entryListForwardHost.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
