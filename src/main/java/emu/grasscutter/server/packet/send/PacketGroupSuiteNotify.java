package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.binout.SceneNpcBornEntry;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GroupSuiteNotifyOuterClass;

import java.util.List;

public class PacketGroupSuiteNotify extends BasePacket {

	/**
	 * Real control which npc suite is loaded
     * EntityNPC is useless
	 */
	public PacketGroupSuiteNotify(List<SceneNpcBornEntry> npcBornEntries) {
		super(PacketOpcodes.GroupSuiteNotify);

		var proto = GroupSuiteNotifyOuterClass.GroupSuiteNotify.newBuilder();

        npcBornEntries.stream()
            .filter(x -> x.getGroupId() > 0 && x.getSuiteIdList() != null)
            .forEach(x -> x.getSuiteIdList().forEach(y ->
                proto.putGroupMap(x.getGroupId(), y)
            ));

		this.setData(proto);

	}
}
