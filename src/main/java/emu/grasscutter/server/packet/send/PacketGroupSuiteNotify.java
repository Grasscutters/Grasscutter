package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.binout.SceneNpcBornEntry;
import emu.grasscutter.game.quest.QuestGroupSuite;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GroupSuiteNotifyOuterClass;
import java.util.*;

public class PacketGroupSuiteNotify extends BasePacket {

    /** Real control which npc suite is loaded EntityNPC is useless */
    public PacketGroupSuiteNotify(List<SceneNpcBornEntry> npcBornEntries) {
        super(PacketOpcodes.GroupSuiteNotify);

        var proto = GroupSuiteNotifyOuterClass.GroupSuiteNotify.newBuilder();

        npcBornEntries.stream()
                .filter(x -> x.getGroupId() > 0 && x.getSuiteIdList() != null)
                .forEach(x -> x.getSuiteIdList().forEach(y -> proto.putGroupMap(x.getGroupId(), y)));

        this.setData(proto);
    }

    public PacketGroupSuiteNotify(int groupId, int suiteId) {
        super(PacketOpcodes.GroupSuiteNotify);

        var proto = GroupSuiteNotifyOuterClass.GroupSuiteNotify.newBuilder();

        proto.putGroupMap(groupId, suiteId);

        this.setData(proto);
    }

    public PacketGroupSuiteNotify(Collection<QuestGroupSuite> questGroupSuites) {
        super(PacketOpcodes.GroupSuiteNotify);

        var proto = GroupSuiteNotifyOuterClass.GroupSuiteNotify.newBuilder();

        questGroupSuites.forEach(i -> proto.putGroupMap(i.getGroup(), i.getSuite()));

        this.setData(proto);
    }
}
