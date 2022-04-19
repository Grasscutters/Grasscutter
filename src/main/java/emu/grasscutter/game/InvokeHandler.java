package emu.grasscutter.game;

import java.util.ArrayList;
import java.util.List;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.proto.ForwardTypeOuterClass.ForwardType;

public class InvokeHandler<T> {
	private final List<T> entryListForwardAll;
	private final List<T> entryListForwardAllExceptCur;
	private final List<T> entryListForwardHost;
	private final Class<? extends GenshinPacket> packetClass;
	
	public InvokeHandler(Class<? extends GenshinPacket> packetClass) {
		this.entryListForwardAll = new ArrayList<>();
		this.entryListForwardAllExceptCur = new ArrayList<>();
		this.entryListForwardHost = new ArrayList<>();
		this.packetClass = packetClass;
	}

	public synchronized void addEntry(ForwardType forward, T entry) {
		switch (forward) {
			case ForwardToAll:
				entryListForwardAll.add(entry);
				break;
			case ForwardToAllExceptCur:
			case ForwardToAllExistExceptCur:
				entryListForwardAllExceptCur.add(entry);
				break;
			case ForwardToHost:
				entryListForwardHost.add(entry);
				break;
			default:
				break;
		}
	}
	
	public synchronized void update(GenshinPlayer player) {
		if (player.getWorld() == null) {
			this.entryListForwardAll.clear();
			this.entryListForwardAllExceptCur.clear();
			this.entryListForwardHost.clear();
			return;
		}
		
		try {
			if (entryListForwardAll.size() > 0) {
				GenshinPacket packet = packetClass.getDeclaredConstructor(List.class).newInstance(this.entryListForwardAll);
				player.getScene().broadcastPacket(packet);
				this.entryListForwardAll.clear();
			}
			if (entryListForwardAllExceptCur.size() > 0) {
				GenshinPacket packet = packetClass.getDeclaredConstructor(List.class).newInstance(this.entryListForwardAllExceptCur);
				player.getScene().broadcastPacketToOthers(player, packet);
				this.entryListForwardAllExceptCur.clear();
			}
			if (entryListForwardHost.size() > 0) {
				GenshinPacket packet = packetClass.getDeclaredConstructor(List.class).newInstance(this.entryListForwardHost);
				player.getWorld().getHost().sendPacket(packet);
				this.entryListForwardHost.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
