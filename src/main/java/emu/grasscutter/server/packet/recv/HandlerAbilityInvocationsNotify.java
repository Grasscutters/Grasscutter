package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.EntityBaseGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilityInvocationsNotifyOuterClass.AbilityInvocationsNotify;
import emu.grasscutter.net.proto.AbilityInvokeEntryOuterClass.AbilityInvokeEntry;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSceneEntityDisappearNotify;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;

@Opcodes(PacketOpcodes.AbilityInvocationsNotify)
public class HandlerAbilityInvocationsNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		AbilityInvocationsNotify notif = AbilityInvocationsNotify.parseFrom(payload);

		Player player = session.getPlayer();
		for (AbilityInvokeEntry entry : notif.getInvokesList()) {
			player.getAbilityManager().onAbilityInvoke(entry);
			player.getAbilityInvokeHandler().addEntry(entry.getForwardType(), entry);
		}
	}

}
